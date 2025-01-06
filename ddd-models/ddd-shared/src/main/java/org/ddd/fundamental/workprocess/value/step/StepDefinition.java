package org.ddd.fundamental.workprocess.value.step;


import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeInfo;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * desc: 工艺流程包括以下一个方面
 * 1、步骤定义,包括生产步骤的内容以及顺序
 * 2、资源配置,包括步骤需要的设备,人员和材料
 * 3、时间管理，估算一个步骤所需要的时间
 * 4、质量控制,每一个步骤设置质量检查点
 *
 * 工序步骤定义
 */
@Embeddable
@MappedSuperclass
@Slf4j
public class StepDefinition implements ValueObject,Cloneable {

    /**
     * 步骤内容信息
     */
    private ChangeInfo stepInfo;

    /**
     * 步骤顺序
     */
    private int order;

    @SuppressWarnings("unused")
    protected StepDefinition(){
    }

    private StepDefinition(ChangeInfo stepInfo, int order){
        this.stepInfo = stepInfo;
        this.order = order;
    }

    public static StepDefinition create(ChangeInfo stepInfo, int order){
        return new StepDefinition(stepInfo, order);
    }

    public StepDefinition changeStepInfo(ChangeInfo stepInfo){
        this.stepInfo = stepInfo;
        return this;
    }

    public StepDefinition changeOrder(int order){
        this.order = order;
        return this;
    }

    public ChangeInfo getStepInfo() {
        return stepInfo.clone();
    }

    public int getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StepDefinition)) return false;
        StepDefinition that = (StepDefinition) o;
        return order == that.order && Objects.equals(stepInfo, that.stepInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepInfo, order);
    }

    @Override
    public String toString() {
        return objToString();
    }

    @Override
    public StepDefinition clone() {
        try {
            StepDefinition clone = (StepDefinition) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
