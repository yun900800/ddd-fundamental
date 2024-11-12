package org.ddd.fundamental.workprocess.value.quantity;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 工序模板关键的数量
 */
@Embeddable
@MappedSuperclass
public class WorkProcessTemplateQuantity implements ValueObject, Cloneable {

    /**
     * 工序模板转交比例
     */
    private Double transferPercent;

    /**
     * 工序模版超交比例
     */
    private Double overCrossPercent;

    /**
     * 工序模板欠交比例
     */
    private Double owePaymentPercent;

    /**
     * 工序模板目标良品率
     */
    private Double targetQualifiedRate;

    @SuppressWarnings("unused")
    private WorkProcessTemplateQuantity(){}

    private WorkProcessTemplateQuantity(double transferPercent,
                                        double overCrossPercent,
                                        double owePaymentPercent,
                                        double targetQualifiedRate){
        this.transferPercent = transferPercent;
        this.overCrossPercent = overCrossPercent;
        this.owePaymentPercent = owePaymentPercent;
        this.targetQualifiedRate = targetQualifiedRate;
    }

    public static TargetStep newBuilder(){
        return new Builder();
    }

    public Double getTransferPercent() {
        return transferPercent;
    }

    public Double getOverCrossPercent() {
        return overCrossPercent;
    }

    public Double getOwePaymentPercent() {
        return owePaymentPercent;
    }

    public Double getTargetQualifiedRate() {
        return targetQualifiedRate;
    }

    public interface TargetStep {
        TransferStep targetQualifiedRate(double targetQualifiedRate);
    }

    public interface TransferStep {
        DeliverStep transferPercent(double transferPercent);
        BuildStep noTransferPercent();
    }

    public interface DeliverStep {
        BuildStep overCrossPercent(double overCrossPercent);
        BuildStep owePaymentPercent(double owePaymentPercent);

        BuildStep noDeliver();
    }


    public interface BuildStep {
        WorkProcessTemplateQuantity build();
    }

    public static class Builder implements TargetStep,
            TransferStep, DeliverStep, BuildStep {

        /**
         * 工序模板转交比例
         */
        private Double transferPercent;

        /**
         * 工序模版超交比例
         */
        private Double overCrossPercent;

        /**
         * 工序模板欠交比例
         */
        private Double owePaymentPercent;

        /**
         * 工序模板目标良品率
         */
        private Double targetQualifiedRate;

        @Override
        public TransferStep targetQualifiedRate(double targetQualifiedRate) {
            this.targetQualifiedRate = targetQualifiedRate;
            return this;
        }

        @Override
        public DeliverStep transferPercent(double transferPercent) {
            this.transferPercent = transferPercent;
            return this;
        }

        @Override
        public BuildStep noTransferPercent() {
            return this;
        }

        @Override
        public BuildStep overCrossPercent(double overCrossPercent) {
            this.overCrossPercent = overCrossPercent;
            return this;
        }

        @Override
        public BuildStep owePaymentPercent(double owePaymentPercent) {
            this.owePaymentPercent = owePaymentPercent;
            return null;
        }

        @Override
        public BuildStep noDeliver() {
            return this;
        }

        @Override
        public WorkProcessTemplateQuantity build() {
            WorkProcessTemplateQuantity quality = new WorkProcessTemplateQuantity();
            quality.targetQualifiedRate = targetQualifiedRate;
            if (transferPercent != null) {
                quality.transferPercent = transferPercent;
            }
            if (overCrossPercent != null) {
                quality.overCrossPercent = overCrossPercent;
            }
            if (owePaymentPercent != null) {
                quality.owePaymentPercent = owePaymentPercent;
            }
            return quality;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessTemplateQuantity)) return false;
        WorkProcessTemplateQuantity quality = (WorkProcessTemplateQuantity) o;
        return Objects.equals(transferPercent, quality.transferPercent) && Objects.equals(overCrossPercent, quality.overCrossPercent) && Objects.equals(owePaymentPercent, quality.owePaymentPercent) && Objects.equals(targetQualifiedRate, quality.targetQualifiedRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transferPercent, overCrossPercent, owePaymentPercent, targetQualifiedRate);
    }

    @Override
    public String toString() {
        return "WorkProcessTemplateQuality{" +
                "transferPercent=" + transferPercent +
                ", overCrossPercent=" + overCrossPercent +
                ", owePaymentPercent=" + owePaymentPercent +
                ", targetQualifiedRate=" + targetQualifiedRate +
                '}';
    }

    @Override
    public WorkProcessTemplateQuantity clone() {
        try {
            WorkProcessTemplateQuantity clone = (WorkProcessTemplateQuantity) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
