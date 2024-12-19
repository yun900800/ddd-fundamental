package org.ddd.fundamental.common.tenant.sql;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.values.ValuesStatement;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Setter
@Slf4j
public abstract class AbstractSqlHandler {
    /**
     * 是否忽略解析错误，此时会直接返回原始SQL而不会抛出异常
     */
    protected boolean ignoreError = false;

    /**
     * 处理SQL语句
     *
     * @param sql SQL语句
     * @return 处理后的SQL语句
     */
    @Nullable
    public String handle(String sql) {
        Statements statements = parseStatements(sql);
        if (Objects.isNull(statements)) {
            return null;
        }
        List<Statement> statementList = statements.getStatements();
        if (CollectionUtils.isEmpty(statementList)) {
            return null;
        }
        return statements.getStatements().stream()
                .map(this::doHandle)
                .map(Statement::toString)
                .collect(Collectors.joining(";"));
    }

    @Nullable
    private Statements parseStatements(String sql) {
        Statements statements = null;
        try {
            statements = CCJSqlParserUtil.parseStatements(sql);
        } catch (JSQLParserException e) {
            log.error("SQL 解析失败: {}", sql, e);
            if (!ignoreError) {
                throw new RuntimeException("SQL 解析失败");
            }
        }
        return statements;
    }

    private Statement doHandle(Statement statement) {
        try {
            if (statement instanceof Select) {
                processSelect(((Select) statement).getSelectBody());
            } else if (statement instanceof Update) {
                processUpdate((Update) statement);
            } else if (statement instanceof Delete) {
                processDelete((Delete) statement);
            } else if (statement instanceof Insert) {
                processInsert((Insert) statement);
            }
        } catch (Exception ex) {
            log.error("SQL 处理失败: {}", statement, ex);
            if (!ignoreError) {
                throw new RuntimeException("SQL 处理失败");
            }
        }
        return statement;
    }

    private void processSelect(SelectBody selectBody) {
        // 普通查询
        if (selectBody instanceof PlainSelect) {
            processSelect((PlainSelect) selectBody);
        }
        // 嵌套查询，比如 select xx from (select yy from t)
        else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                processSelect(withItem.getSelectBody());
            }
        }
        // 联合查询，比如 union
        else if (selectBody instanceof SetOperationList) {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (!CollectionUtils.isEmpty(operationList.getSelects())) {
                operationList.getSelects().forEach(this::processSelect);
            }
        }
        // 值查询，比如 select 1, 2, 3
        else if (selectBody instanceof ValuesStatement) {
            List<Expression> expressions = ((ValuesStatement) selectBody).getExpressions();
            if (!CollectionUtils.isEmpty(expressions)) {
                expressions.forEach(exp -> processCondition(exp, null));
            }
        } else {
            log.error("无法解析的 select 语句：{}({})", selectBody, selectBody.getClass());
            throw new RuntimeException("不支持的查询语句：" + selectBody.getClass().getName());
        }
    }

    /**
     * 处理插入语句
     *
     * @param insert 插入语句
     */
    protected void processInsert(Insert insert) {
        // do nothing
    }

    /**
     * 处理删除语句
     *
     * @param delete 删除语句
     */
    protected void processDelete(Delete delete) {
        Table table = delete.getTable();
        delete.setWhere(processCondition(delete.getWhere(), table));
        // 如果还存在关联查询
        List<Join> joins = delete.getJoins();
        if (!CollectionUtils.isEmpty(joins)) {
            joins.forEach(this::processJoin);
        }
    }

    /**
     * 处理更新语句
     *
     * @param update 更新语句
     */
    protected void processUpdate(Update update) {
        Table table = update.getTable();
        update.setWhere(processCondition(update.getWhere(), table));
        // 如果还存在关联查询
        List<Join> joins = update.getJoins();
        if (!CollectionUtils.isEmpty(joins)) {
            joins.forEach(this::processJoin);
        }
    }

    /**
     * 处理查询语句
     *
     * @param plainSelect 查询语句
     */
    protected void processSelect(PlainSelect plainSelect) {
        FromItem fromItem = plainSelect.getFromItem();
        // 如果是普通的表名
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;
            plainSelect.setFromItem(handleTable(fromTable));
            plainSelect.setWhere(processCondition(plainSelect.getWhere(), fromTable));
        }
        // 如果是子查询，比如 select * from (select xxx from yyy)
        else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelect(subSelect.getSelectBody());
            }
            plainSelect.setWhere(processCondition(plainSelect.getWhere(), subSelect));
        }
        // 如果是带有特殊函数的子查询，比如 lateral (select sum(*) from yyy)
        else if (fromItem instanceof SpecialSubSelect) {
            SpecialSubSelect specialSubSelect = (SpecialSubSelect) fromItem;
            if (specialSubSelect.getSubSelect() != null) {
                SubSelect subSelect = specialSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelect(subSelect.getSelectBody());
                }
            }
            plainSelect.setWhere(processCondition(plainSelect.getWhere(), specialSubSelect));
        }
        // 未知类型的查询，直接报错
        else {
            log.error("无法解析的 from 语句：{}({})", fromItem, fromItem.getClass());
            throw new RuntimeException("不支持的查询语句：" + fromItem.getClass().getName());
        }

        // 如果还存在关联查询
        List<Join> joins = plainSelect.getJoins();
        if (!CollectionUtils.isEmpty(joins)) {
            joins.forEach(this::processJoin);
        }
    }

    /**
     * 处理关联查询
     *
     * @param join 关联查询
     */
    protected void processJoin(Join join) {
        FromItem joinTable = join.getRightItem();
        if (joinTable instanceof Table) {
            Table table = (Table) joinTable;
            join.setRightItem(handleTable((Table) joinTable));
            join.setOnExpression(processCondition(join.getOnExpression(), table));
        }
        else if (joinTable instanceof SubSelect) {
            processSelect(((SubSelect) joinTable).getSelectBody());
        }
        else if (joinTable instanceof SpecialSubSelect) {
            SpecialSubSelect specialSubSelect = (SpecialSubSelect) joinTable;
            if (specialSubSelect.getSubSelect() != null) {
                SubSelect subSelect = specialSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelect(subSelect.getSelectBody());
                }
            }
        }
        else {
            log.error("无法解析的 join 语句：{}({})", joinTable, joinTable.getClass());
            throw new RuntimeException("不支持的查询语句：" + joinTable.getClass().getName());
        }
    }

    /**
     * <p>获取添加了租户条件的查询条件，若条件中存在子查询，则也会为子查询添加租户条件。
     *
     * @param expression 条件表达式
     * @param table 表
     * @return 添加租户条件后的条件表达式
     */
    @SuppressWarnings({"java:S6541", "java:S3776"})
    protected Expression processCondition(@Nullable Expression expression, FromItem table) {
        // 如果已经不可拆分的表达式，则直接返回
        if (isBasicExpression(expression)) {
            return expression;
        }
        // 如果是子查询，则需要对子查询进行递归处理
        else if (expression instanceof SubSelect) {
            processSelect(((SubSelect) expression).getSelectBody());
        }
        // 如果是 in 条件，比如：xxx in (select xx from yy……)，则需要对子查询进行递归处理
        else if (expression instanceof InExpression) {
            InExpression inExp = (InExpression) expression;
            ItemsList rightItems = inExp.getRightItemsList();
            if (rightItems instanceof SubSelect) {
                processSelect(((SubSelect) rightItems).getSelectBody());
            }
        }
        // 如果是 not 或者 != 条件，则需要对里面的条件进行递归处理
        else if (expression instanceof NotExpression) {
            NotExpression notExpression = (NotExpression) expression;
            processCondition(notExpression.getExpression(), table);
        }
        // 如果是 (xxx != xxx)，则需要对括号里面的表达式进行递归处理
        else if (expression instanceof Parenthesis) {
            Parenthesis parenthesis = (Parenthesis) expression;
            Expression content = parenthesis.getExpression();
            processCondition(content, table);
        }
        // 如果是二元表达式，比如：xx = xx，xx > xx，则需要对左右两边的表达式进行递归处理
        else if (expression instanceof BinaryExpression) {
            BinaryExpression binaryExpression = (BinaryExpression) expression;
            Expression left = binaryExpression.getLeftExpression();
            processCondition(left, table);
            Expression right = binaryExpression.getRightExpression();
            processCondition(right, table);
        }
        // 如果是函数，比如：if(xx, xx) ，则需要对函数的参数进行递归处理
        else if (expression instanceof Function) {
            Function function = (Function) expression;
            ExpressionList parameters = function.getParameters();
            if (parameters != null) {
                parameters.getExpressions().forEach(param -> processCondition(param, table));
            }
        }
        // 如果是 case when 语句，则需要对 when 和 then 两个条件进行递归处理
        else if (expression instanceof WhenClause) {
            WhenClause whenClause = (WhenClause) expression;
            processCondition(whenClause.getWhenExpression(), table);
            processCondition(whenClause.getThenExpression(), table);
        }
        // 如果是 case 语句，则需要对 switch、when、then、else 四个条件进行递归处理
        else if (expression instanceof CaseExpression) {
            CaseExpression caseExpression = (CaseExpression) expression;
            processCondition(caseExpression.getSwitchExpression(), table);
            List<WhenClause> whenClauses = caseExpression.getWhenClauses();
            if (!CollectionUtils.isEmpty(whenClauses)) {
                whenClauses.forEach(whenClause -> {
                    processCondition(whenClause.getWhenExpression(), table);
                    processCondition(whenClause.getThenExpression(), table);
                });
            }
            processCondition(caseExpression.getElseExpression(), table);
        }
        // 如果是 exists 语句，比如：exists (select xx from yy……)，则需要对子查询进行递归处理
        else if (expression instanceof ExistsExpression) {
            Expression existsExpression = ((ExistsExpression) expression).getRightExpression();
            if (existsExpression instanceof SubSelect) {
                processSelect(((SubSelect) existsExpression).getSelectBody());
            }
        }
        // 如果是 all 或者 any 语句，比如：xx > all (select xx from yy……)，则需要对子查询进行递归处理
        else if (expression instanceof AllComparisonExpression) {
            AllComparisonExpression allComparisonExpression = (AllComparisonExpression) expression;
            processSelect(allComparisonExpression.getSubSelect().getSelectBody());
        }
        else if (expression instanceof AnyComparisonExpression) {
            AnyComparisonExpression anyComparisonExpression = (AnyComparisonExpression) expression;
            processSelect(anyComparisonExpression.getSubSelect().getSelectBody());
        }
        // 如果是 cast 语句，比如：cast(xx as xx)，则需要对子查询进行递归处理
        else if (expression instanceof CastExpression) {
            CastExpression castExpression = (CastExpression) expression;
            processCondition(castExpression.getLeftExpression(), table);
        }

        // 拼接查询条件
        Expression appendCondition = handleCondition(expression, table);
        return Objects.isNull(appendCondition) ? expression : appendCondition;
    }

    /**
     * 返回一个查询条件，该查询条件将替换{@code table}原有的{@code where}条件
     *
     * @param expression 原有的查询条件
     * @param table 指定的表
     * @return 查询条件
     */
    protected abstract Expression handleCondition(@Nullable Expression expression, FromItem table);

    /**
     * 返回一个表名，该表名将替换原有的表名
     *
     * @param table 表名
     * @return 处理后的表名
     */
    protected FromItem handleTable(Table table) {
        return table;
    }

    /**
     * 判断是否是已经是无法再拆分的基本表达式 <br/>
     * 比如：列名、常量、函数等
     *
     * @param expression 表达式
     * @return 是否是基本表达式
     */
    protected boolean isBasicExpression(@Nullable Expression expression) {
        return expression instanceof Column
                || expression instanceof LongValue
                || expression instanceof StringValue
                || expression instanceof DoubleValue
                || expression instanceof NullValue
                || expression instanceof TimeValue
                || expression instanceof TimestampValue
                || expression instanceof DateValue;
    }
}
