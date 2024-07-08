package org.ddd.fundamental.domain.revenue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Contract {

    private Product product;

    private double revenues;

    private Date whenSigned;

    private Long id;

    private List<RevenueRecognition> revenueRecognitions = new ArrayList<>();

    public Contract(Product product, double revenues, Date whenSigned) {
        this.product = product;
        this.revenues = revenues;
        this.whenSigned = whenSigned;
    }

    public double recognizeRevenue(Date asOf){
        double sum = 0.0;
        for (RevenueRecognition revenueRecognition:revenueRecognitions) {
            if (revenueRecognition.isRecognizableBy(asOf)){
                sum+=revenueRecognition.getAmount();
            }
        }
        return sum;
    }

    public void addRevenueRecognition(RevenueRecognition revenueRecognition){
        this.revenueRecognitions.add(revenueRecognition);
    }

    public void calculateRecognitions(){
        product.calculateRevenueRecognition(this);
    }

    public Product getProduct() {
        return product;
    }

    public double getRevenues() {
        return revenues;
    }

    public Date getWhenSigned() {
        return whenSigned;
    }

    public Long getId() {
        return id;
    }
}
