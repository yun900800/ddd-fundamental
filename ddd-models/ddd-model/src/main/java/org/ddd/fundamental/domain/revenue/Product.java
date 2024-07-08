package org.ddd.fundamental.domain.revenue;

import org.ddd.fundamental.constants.ProductType;

/**
 * 产品
 */
public class Product {

    /**
     * 产品类型(决定收入确认策略)
     */
    private ProductType productType;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品收入确认策略
     */
    private RecognitionStrategy recognitionStrategy;

    public Product(ProductType productType,
                   String name,
                   RecognitionStrategy recognitionStrategy){
        this.name = name;
        this.productType = productType;
        this.recognitionStrategy = recognitionStrategy;
    }

    public static Product newWordProcessor(String name){
        return new Product( ProductType.WORD, name, new CompleteRecognitionStrategy());
    }

    public static Product newExcelProcessor(String name){
        return new Product( ProductType.EXCEL, name, new ThreeWayRecognitionStrategy(60,90));
    }

    public static Product newDatabaseProcessor(String name){
        return new Product( ProductType.DATABASE, name, new ThreeWayRecognitionStrategy(30,60));
    }

    public void calculateRevenueRecognition(Contract contract) {
        recognitionStrategy.calculateRevenueRecognitions(contract);
    }


    public ProductType getProductType() {
        return productType;
    }

    public String getName() {
        return name;
    }

    public RecognitionStrategy getRecognitionStrategy() {
        return recognitionStrategy;
    }
}
