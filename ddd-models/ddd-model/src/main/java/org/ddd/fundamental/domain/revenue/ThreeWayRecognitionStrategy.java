package org.ddd.fundamental.domain.revenue;

import org.ddd.fundamental.helper.DateHelper;

public class ThreeWayRecognitionStrategy implements RecognitionStrategy{

    private int firstRecognitionOffset;

    private int secondRecognitionOffset;

    public ThreeWayRecognitionStrategy(int firstRecognitionOffset,
                                       int secondRecognitionOffset){
        this.firstRecognitionOffset = firstRecognitionOffset;
        this.secondRecognitionOffset = secondRecognitionOffset;
    }



    @Override
    public void calculateRevenueRecognitions(Contract contract) {
        double revenues = contract.getRevenues();
        contract.addRevenueRecognition(
                new RevenueRecognition(revenues/3, contract.getWhenSigned())
        );
        contract.addRevenueRecognition(
                new RevenueRecognition(revenues/3,
                        DateHelper.addInterval(firstRecognitionOffset,contract.getWhenSigned()))
        );
        contract.addRevenueRecognition(
                new RevenueRecognition(revenues/3,
                        DateHelper.addInterval(secondRecognitionOffset,contract.getWhenSigned()))
        );
    }
}
