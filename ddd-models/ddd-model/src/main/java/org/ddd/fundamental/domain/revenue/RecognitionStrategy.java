package org.ddd.fundamental.domain.revenue;

/**
 * 收入确认策略接口
 */
public interface RecognitionStrategy {

    void calculateRevenueRecognitions(Contract contract);

}
