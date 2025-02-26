package org.ddd.fundamental.material.hierarchy;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.value.ProductStructure;
import org.ddd.fundamental.core.DomainObjectId;
import org.ddd.fundamental.core.ValueObject;
import org.springframework.util.CollectionUtils;


import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 尝试构建一个结构化的数据对象,都使用泛型参数
 * @param <ID>
 * @param <T>
 */
@Embeddable
@MappedSuperclass
@Slf4j
public abstract class BaseHierarchy<ID extends DomainObjectId, T extends ValueObject, U extends BaseHierarchy> implements ValueObject, Cloneable {

    private ID id;

    private ID parentId;

    private T data;

    private Set<U> children;

    @SuppressWarnings("unused")
    private BaseHierarchy(){}

    public BaseHierarchy(ID id, ID parentId, T data){
        this.id = id;
        this.parentId = parentId;
        this.data = data;
        this.children = new HashSet<>();
    }

    public ID getId() {
        return id;
    }

    public ID getParentId() {
        return parentId;
    }

    public T getData() {
        return data;
    }

    public Set<U> getChildren() {
        return new HashSet<>(children);
    }

    abstract void canAddedToHierarchy(U node);

    public BaseHierarchy addNode(U node) {
        canAddedToHierarchy(node);
        this.children.add(node);
        return this;
    }

    abstract void canRemovedToHierarchy(U node);

    public BaseHierarchy removeNode(U node) {
        canRemovedToHierarchy(node);
        this.children.remove(node);
        return this;
    }

    public BaseHierarchy clearNode(){
        this.children.clear();
        return this;
    }

    public <DATA> DATA traverse(TraverseNode<U,DATA> fn, U node, DATA data){
        DATA result = fn.handle(data,node);
        if (CollectionUtils.isEmpty(node.getChildren())){
            return null;
        }
        for (Object temp: node.getChildren()) {
            DATA tempResult = result;
            result = traverse(fn,(U)temp,result);
            if (null == result){
                result = tempResult;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseHierarchy)) return false;
        BaseHierarchy<?, ?, ?> that = (BaseHierarchy<?, ?, ?>) o;
        return Objects.equals(id, that.id) && Objects.equals(parentId, that.parentId)
                && Objects.equals(data, that.data) && Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, data, children);
    }

    @Override
    public String toString() {
        return objToString();
    }
}
