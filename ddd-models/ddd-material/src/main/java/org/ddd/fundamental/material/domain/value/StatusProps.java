package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class StatusProps implements ValueObject, Cloneable {

    private String batchStatus;

    private String qualityStatus;

    private String materialStatus;

    private String transferStatus;

    private String handlingStatus;

    @SuppressWarnings("unused")
    private StatusProps(){}

    public StatusProps(String batchStatus,String qualityStatus,
                       String materialStatus,String transferStatus,
                       String handlingStatus){
        this.batchStatus = batchStatus;
        this.qualityStatus = qualityStatus;
        this.materialStatus = materialStatus;
        this.transferStatus = transferStatus;
        this.handlingStatus = handlingStatus;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public String getQualityStatus() {
        return qualityStatus;
    }

    public String getMaterialStatus() {
        return materialStatus;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public String getHandlingStatus() {
        return handlingStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusProps)) return false;
        StatusProps that = (StatusProps) o;
        return Objects.equals(batchStatus, that.batchStatus) && Objects.equals(qualityStatus, that.qualityStatus) && Objects.equals(materialStatus, that.materialStatus) && Objects.equals(transferStatus, that.transferStatus) && Objects.equals(handlingStatus, that.handlingStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchStatus, qualityStatus, materialStatus, transferStatus, handlingStatus);
    }

    @Override
    public String toString() {
        return "StatusProps{" +
                "batchStatus='" + batchStatus + '\'' +
                ", qualityStatus='" + qualityStatus + '\'' +
                ", materialStatus='" + materialStatus + '\'' +
                ", transferStatus='" + transferStatus + '\'' +
                ", handlingStatus='" + handlingStatus + '\'' +
                '}';
    }

    @Override
    public StatusProps clone() {
        try {
            StatusProps clone = (StatusProps) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
