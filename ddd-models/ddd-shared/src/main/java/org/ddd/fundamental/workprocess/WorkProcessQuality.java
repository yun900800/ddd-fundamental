package org.ddd.fundamental.workprocess;


import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 工序数量类定义了工序的几个关键数量
 */
@Embeddable
@MappedSuperclass
public class WorkProcessQuality implements ValueObject, Cloneable {

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

    /**
     * 超交百分比
     */
    private Integer overCrossPercent;

    /**
     * 欠交百分比
     */
    private Integer owePaymentPercent;

    @SuppressWarnings("unused")
    private WorkProcessQuality(){}

    private WorkProcessQuality(int targetQuantity,
                               int unQualifiedQuantity,
                               int transferQuantity,
                               Integer overCrossQuantity,
                               Integer owePaymentQuantity,
                               Integer overCrossPercent,
                               Integer owePaymentPercent){
        this.targetQuantity = targetQuantity;
        this.unQualifiedQuantity = unQualifiedQuantity;
        this.transferQuantity = transferQuantity;
        this.overCrossQuantity = overCrossQuantity;
        this.owePaymentQuantity = owePaymentQuantity;
        this.overCrossPercent = overCrossPercent;
        this.owePaymentPercent = owePaymentPercent;
    }

    public static WorkProcessQuality create(int targetQuantity,
                                            int unQualifiedQuantity,
                                            int transferQuantity,
                                            Integer overCrossQuantity,
                                            Integer owePaymentQuantity,
                                            Integer overCrossPercent,
                                            Integer owePaymentPercent){
        return new WorkProcessQuality(targetQuantity,unQualifiedQuantity,transferQuantity
        ,overCrossQuantity,owePaymentQuantity,overCrossPercent,owePaymentPercent);
    }

    public WorkProcessQuality changeTargetQuantity(int targetQuantity){
        return new WorkProcessQuality(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity,overCrossPercent,owePaymentPercent);
    }

    public WorkProcessQuality changeUnQualifiedQuantity(int unQualifiedQuantity) {
        return new WorkProcessQuality(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity,overCrossPercent,owePaymentPercent);
    }

    public WorkProcessQuality changeTransferQuantity(int transferQuantity){
        return new WorkProcessQuality(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity,overCrossPercent,owePaymentPercent);
    }

    private void checkOwePayment(){
        if (null != owePaymentQuantity || null != owePaymentPercent) {
            throw new RuntimeException("存在欠交数量或者百分比,请检查一下数据");
        }
    }

    private void checkOverCross(){
        if (null != overCrossPercent || null != overCrossQuantity) {
            throw new RuntimeException("存在超交数量或者百分比,请检查一下数据");
        }
    }

    public WorkProcessQuality changeOverCrossQuantity(int overCrossQuantity){
        checkOwePayment();
        return new WorkProcessQuality(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity,overCrossPercent,owePaymentPercent);
    }

    public WorkProcessQuality changeOverCrossPercent(int overCrossPercent){
        checkOwePayment();
        return new WorkProcessQuality(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity,overCrossPercent,owePaymentPercent);
    }

    public WorkProcessQuality changeOwePaymentQuantity(int owePaymentQuantity){
        checkOverCross();
        return new WorkProcessQuality(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity,overCrossPercent,owePaymentPercent);
    }

    public WorkProcessQuality changeOwePaymentPercent(int owePaymentPercent){
        checkOverCross();
        return new WorkProcessQuality(targetQuantity,unQualifiedQuantity,transferQuantity
                ,overCrossQuantity,owePaymentQuantity,overCrossPercent,owePaymentPercent);
    }

    public static TargetStep newBuilder(){
        return new Builder();
    }

    @Override
    public WorkProcessQuality clone() {
        try {
            WorkProcessQuality clone = (WorkProcessQuality) super.clone();
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

        MainStep transferQuantityWithOverCross(int transferQuantity);

    }

    public interface MainStep {
        OverCrossPercentStep withOverCrossQuantity(int overCrossQuantity);

        OwePaymentPercentStep withOwePaymentQuantity(int owePaymentQuantity);

        OverCrossQuantityStep withOverCrossPercent(int overCrossPercent);

        OwePaymentQuantityStep withOwePaymentPercent(int owePaymentPercent);
    }

    public interface OverCrossPercentStep {
        BuildStep overCrossPercent(int overCrossPercent);

        BuildStep noOverCrossPercent();
    }

    public interface OverCrossQuantityStep {
        BuildStep overCrossQuantity(int overCrossQuantity);

        BuildStep noOverCrossQuantity();
    }


    public interface OwePaymentPercentStep {
        BuildStep owePaymentPercent(int owePaymentPercent);

        BuildStep noOwePaymentPercent();
    }

    public interface OwePaymentQuantityStep {
        BuildStep owePaymentQuantity(int owePaymentQuantity);

        BuildStep noOwePaymentQuantity();
    }



    public interface BuildStep {
        WorkProcessQuality build();
    }

    public static class Builder implements TargetStep,
            UnQualifiedStep,TransferStep, MainStep,
            OwePaymentPercentStep, OverCrossPercentStep,
            OverCrossQuantityStep, OwePaymentQuantityStep,
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

        /**
         * 超交百分比
         */
        private Integer overCrossPercent;

        /**
         * 欠交百分比
         */
        private Integer owePaymentPercent;

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
        public MainStep transferQuantityWithOverCross(int transferQuantity) {
            this.transferQuantity = transferQuantity;
            return this;
        }

        @Override
        public BuildStep overCrossQuantity(int overCrossQuantity) {
            this.overCrossQuantity = overCrossQuantity;
            return this;
        }

        @Override
        public BuildStep noOverCrossQuantity() {
            return this;
        }



        @Override
        public OverCrossPercentStep withOverCrossQuantity(int overCrossQuantity) {
            this.overCrossQuantity = overCrossQuantity;
            return this;
        }

        @Override
        public OwePaymentPercentStep withOwePaymentQuantity(int owePaymentQuantity) {
            this.owePaymentQuantity = owePaymentQuantity;
            return this;
        }

        @Override
        public OverCrossQuantityStep withOverCrossPercent(int overCrossPercent) {
            this.overCrossPercent = overCrossPercent;
            return this;
        }

        @Override
        public OwePaymentQuantityStep withOwePaymentPercent(int owePaymentPercent) {
            this.owePaymentPercent = owePaymentPercent;
            return this;
        }

        @Override
        public BuildStep owePaymentQuantity(int owePaymentQuantity) {
            this.owePaymentQuantity = owePaymentQuantity;
            return this;
        }

        @Override
        public BuildStep noOwePaymentQuantity() {
            return this;
        }

        @Override
        public BuildStep owePaymentPercent(int owePaymentPercent) {
            this.owePaymentPercent = owePaymentPercent;
            return this;
        }

        @Override
        public BuildStep noOwePaymentPercent() {
            return this;
        }


        @Override
        public WorkProcessQuality build() {
            WorkProcessQuality workProcessQuality = new WorkProcessQuality();
            workProcessQuality.targetQuantity = targetQuantity;
            workProcessQuality.unQualifiedQuantity = unQualifiedQuantity;
            workProcessQuality.transferQuantity = transferQuantity;
            if (null!= owePaymentQuantity) {
                workProcessQuality.owePaymentQuantity = owePaymentQuantity;
            }
            if (null!= owePaymentPercent) {
                workProcessQuality.owePaymentPercent = owePaymentPercent;
            }
            if (null!= overCrossPercent) {
                workProcessQuality.overCrossPercent = overCrossPercent;
            }
            if (null!= overCrossQuantity) {
                workProcessQuality.overCrossQuantity = overCrossQuantity;
            }
            return workProcessQuality;
        }

        @Override
        public BuildStep overCrossPercent(int overCrossPercent) {
            this.overCrossPercent   = overCrossPercent;
            return this;
        }

        @Override
        public BuildStep noOverCrossPercent() {
            return this;
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

    public Integer getOverCrossPercent() {
        return overCrossPercent;
    }

    public Integer getOwePaymentPercent() {
        return owePaymentPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessQuality)) return false;
        WorkProcessQuality that = (WorkProcessQuality) o;
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
                ", overCrossPercent=" + overCrossPercent +
                ", owePaymentPercent=" + owePaymentPercent +
                '}';
    }
}
