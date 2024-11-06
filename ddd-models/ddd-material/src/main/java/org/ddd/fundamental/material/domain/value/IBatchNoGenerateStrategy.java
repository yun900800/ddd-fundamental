package org.ddd.fundamental.material.domain.value;

/**
 * 生成批次号的接口
 */
public interface IBatchNoGenerateStrategy {
    String generate(IBatch batch);
}
