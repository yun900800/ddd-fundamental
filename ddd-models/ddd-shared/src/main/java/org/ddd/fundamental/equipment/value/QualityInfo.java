package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class QualityInfo implements ValueObject,Cloneable {

    /**
     * 设备校验状态
     */
    private String checkStatus;

    /**
     * 设备校验计划
     */
    private String checkPlan;

    /**
     * 校验结果
     */
    private boolean checkResult;

    @SuppressWarnings("unused")
    private QualityInfo(){

    }

    private QualityInfo(String checkStatus,
                       String checkPlan,
                       boolean checkResult){
        this.checkPlan = checkPlan;
        this.checkStatus = checkStatus;
        this.checkResult = checkResult;
    }

    private QualityInfo(String checkStatus,
                       String checkPlan){
        this(checkStatus,checkPlan,true);
    }

    public static QualityInfo create(String checkStatus,
                                     String checkPlan,
                                     boolean checkResult){
        return new QualityInfo(checkStatus,checkPlan,checkResult);
    }

    public static QualityInfo create(String checkStatus,
                                     String checkPlan){
        return new QualityInfo(checkStatus,checkPlan);
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public String getCheckPlan() {
        return checkPlan;
    }

    public boolean isCheckResult() {
        return checkResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QualityInfo)) return false;
        QualityInfo that = (QualityInfo) o;
        return checkResult == that.checkResult && Objects.equals(checkStatus, that.checkStatus) && Objects.equals(checkPlan, that.checkPlan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkStatus, checkPlan, checkResult);
    }

    @Override
    public String toString() {
        return "QualityInfo{" +
                "checkStatus='" + checkStatus + '\'' +
                ", checkPlan='" + checkPlan + '\'' +
                ", checkResult=" + checkResult +
                '}';
    }

    @Override
    public QualityInfo clone() {
        try {
            QualityInfo clone = (QualityInfo) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
