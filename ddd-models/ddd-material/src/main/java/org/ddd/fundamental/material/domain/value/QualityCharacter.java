package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 质量特性
 */
@Embeddable
@MappedSuperclass
public class QualityCharacter implements ValueObject, Cloneable {

    private String qualityDesc;

    private String qualityReason;

    @SuppressWarnings("unused")
    private QualityCharacter(){}

    private QualityCharacter(String qualityDesc,String qualityReason){
        this.qualityDesc = qualityDesc;
        this.qualityReason = qualityReason;
    }

    public static QualityCharacter create(String qualityDesc,String qualityReason){
        return new QualityCharacter(qualityDesc,qualityReason);
    }

    public String getQualityDesc() {
        return qualityDesc;
    }

    public String getQualityReason() {
        return qualityReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QualityCharacter)) return false;
        QualityCharacter that = (QualityCharacter) o;
        return Objects.equals(qualityDesc, that.qualityDesc) && Objects.equals(qualityReason, that.qualityReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qualityDesc, qualityReason);
    }

    @Override
    public String toString() {
        return "QualityCharacter{" +
                "qualityDesc='" + qualityDesc + '\'' +
                ", qualityReason='" + qualityReason + '\'' +
                '}';
    }

    @Override
    public QualityCharacter clone() {
        try {
            QualityCharacter clone = (QualityCharacter) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
