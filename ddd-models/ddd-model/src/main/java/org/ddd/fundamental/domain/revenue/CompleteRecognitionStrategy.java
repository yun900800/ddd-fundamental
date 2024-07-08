package org.ddd.fundamental.domain.revenue;

public class CompleteRecognitionStrategy implements RecognitionStrategy{
    @Override
    public void calculateRevenueRecognitions(Contract contract) {
        contract.addRevenueRecognition(
                new RevenueRecognition(contract.getRevenues(),contract.getWhenSigned())
        );
    }
}
