package org.ddd.fundamental.workprocess.value.relationship;

import org.ddd.fundamental.core.ValueObject;

/**
 * 定义工序关系, 一个工序对应多个工序关系
 * 同一个工序定义多个工序关系。 但是，每个工序关系特定于一道工序，
 * 并且存储特定于工艺路线、发布的产品或与物料组有关的一组产品的属性
 *
 * 工序关系的范围通过物料代码、物料关系、工艺路线代码和工艺路线关系属性定义
 * 工序关系的操作属性
 * 1、成本类别
 * 2、消耗参数
 * 3、处理时间
 * 4、处理数量
 * 5、资源要求
 * 6、注释与说明
 */
public class ProcessRelationShip implements ValueObject {

}
