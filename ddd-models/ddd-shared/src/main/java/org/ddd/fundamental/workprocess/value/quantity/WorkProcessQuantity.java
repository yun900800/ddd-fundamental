package org.ddd.fundamental.workprocess.value.quantity;


import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 工序数量类定义了工序的几个关键数量
 */
@Embeddable
@MappedSuperclass
public class WorkProcessQuantity implements ValueObject, Cloneable {

    /**
     * 工序目标数量
     */
    private int targetQuantity;

    /**
     * 工序不良品数量
     */
    private int unQualifiedQuantity;

    /**
     * 工序转交数量
     */
    private int transferQuantity;

    /**
     * 超交数量
     */
    private Integer overCrossQuantity;

    /**
     * 欠交数量
     */
    private Integer owePaymentQuantity;


    @SuppressWarnings("unused")
    private WorkProcessQuantity(){}

    private WorkProcessQuantity(int targetQuantity,
                                int unQualifiedQuantity,
                                int transferQuantity,
                                Integer overCrossQuantity,
                                Integer owePaymentQuantity){
        this.targetQuantity = targetQuantity;
        this.unQualifiedQuantity = unQualifiedQuantity;
        this.transferQuantity = transferQuantity;
        this.overCrossQuantity = overCrossQuantity;
        this.owePaymentQuantity = owePaymentQuantity;
    }

    public static WorkProcessQuantity create(int targetQuantity,
                                             int unQualifiedQuantity,
                                             int transferQuantity,
                                             Integer overCrossQuantity,
                                             Integer owePaymentQuantity){
        return new WorkProcessQuantity(targetQuantity,unQualifiedQuantity,transferQuantity
        ,overCrossQuantity,owePaymentQuantity);
    }

    public WorkProcessQuantity changeTargetQuantity(int targetQuantity){
        return new WorkProcessQuantity(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity);
    }

    public WorkProcessQuantity changeUnQualifiedQuantity(int unQualifiedQuantity) {
        return new WorkProcessQuantity(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity);
    }

    public WorkProcessQuantity changeTransferQuantity(int transferQuantity){
        return new WorkProcessQuantity(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity);
    }


    public WorkProcessQuantity changeOverCrossQuantity(int overCrossQuantity){
        return new WorkProcessQuantity(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity);
    }

    public WorkProcessQuantity changeOwePaymentQuantity(int owePaymentQuantity){
        return new WorkProcessQuantity(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity);
    }

    public static TargetStep newBuilder(){
        return new Builder();
    }

    @Override
    public WorkProcessQuantity clone() {
        try {
            WorkProcessQuantity clone = (WorkProcessQuantity) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public interface TargetStep {
        UnQualifiedStep targetQuantity(int targetQuantity);
    }

    public interface UnQualifiedStep {
        TransferStep unQualifiedQuantity(int unQualifiedQuantity);
    }

    public interface TransferStep {

        QuantityStep transferQuantityWithOverCross(int transferQuantity);

    }

    public interface QuantityStep {

        BuildStep owePaymentQuantity(int owePaymentQuantity);

        BuildStep overCrossQuantity(int overCrossQuantity);
    }


//    public interface OverCrossQuantityStep {
//        OwePaymentQuantityStep overCrossQuantity(int overCrossQuantity);
//
//        BuildStep noOverCrossQuantity();
//    }
//
//
//
//    public interface OwePaymentQuantityStep {
//        OverCrossQuantityStep owePaymentQuantity(int owePaymentQuantity);
//
//        BuildStep noOwePaymentQuantity();
//    }



    public interface BuildStep {
        WorkProcessQuantity build();
    }

    public static class Builder implements TargetStep,
            UnQualifiedStep,TransferStep, QuantityStep,
            BuildStep {

        /**
         * 工序目标数量
         */
        private int targetQuantity;

        /**
         * 工序不良品数量
         */
        private int unQualifiedQuantity;

        /**
         * 工序转交数量
         */
        private int transferQuantity;

        /**
         * 超交数量
         */
        private Integer overCrossQuantity;

        /**
         * 欠交数量
         */
        private Integer owePaymentQuantity;

        @Override
        public UnQualifiedStep targetQuantity(int targetQuantity) {
            this.targetQuantity = targetQuantity;
            return this;
        }

        @Override
        public TransferStep unQualifiedQuantity(int unQualifiedQuantity) {
            this.unQualifiedQuantity = unQualifiedQuantity;
            return this;
        }

        @Override
        public QuantityStep transferQuantityWithOverCross(int transferQuantity) {
            this.transferQuantity = transferQuantity;
            return this;
        }

        @Override
        public BuildStep overCrossQuantity(int overCrossQuantity) {
            this.overCrossQuantity = overCrossQuantity;
            return this;
        }




        @Override
        public BuildStep owePaymentQuantity(int owePaymentQuantity) {
            this.owePaymentQuantity = owePaymentQuantity;
            return this;
        }



        @Override
        public WorkProcessQuantity build() {
            WorkProcessQuantity workProcessQuality = new WorkProcessQuantity();
            workProcessQuality.targetQuantity = targetQuantity;
            workProcessQuality.unQualifiedQuantity = unQualifiedQuantity;
            workProcessQuality.transferQuantity = transferQuantity;
            if (null!= owePaymentQuantity) {
                workProcessQuality.owePaymentQuantity = owePaymentQuantity;
            }
            if (null!= overCrossQuantity) {
                workProcessQuality.overCrossQuantity = overCrossQuantity;
            }
            return workProcessQuality;
        }
    }



    public int getTargetQuantity() {
        return targetQuantity;
    }

    public int getUnQualifiedQuantity() {
        return unQualifiedQuantity;
    }

    public int getTransferQuantity() {
        return transferQuantity;
    }

    public Integer getOverCrossQuantity() {
        return overCrossQuantity;
    }

    public Integer getOwePaymentQuantity() {
        return owePaymentQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessQuantity)) return false;
        WorkProcessQuantity that = (WorkProcessQuantity) o;
        return targetQuantity == that.targetQuantity && unQualifiedQuantity == that.unQualifiedQuantity && transferQuantity == that.transferQuantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetQuantity, unQualifiedQuantity, transferQuantity);
    }

    @Override
    public String toString() {
        return "WorkProcessQuality{" +
                "targetQuantity=" + targetQuantity +
                ", unQualifiedQuantity=" + unQualifiedQuantity +
                ", transferQuantity=" + transferQuantity +
                ", overCrossQuantity=" + overCrossQuantity +
                ", owePaymentQuantity=" + owePaymentQuantity +
                '}';
    }
}
