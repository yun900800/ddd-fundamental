package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * 资源池
 */
@Embeddable
@MappedSuperclass
public class ResourcesMatrixPool implements ValueObject, Cloneable {

}
