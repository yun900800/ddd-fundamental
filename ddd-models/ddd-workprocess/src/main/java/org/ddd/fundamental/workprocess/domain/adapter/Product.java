package org.ddd.fundamental.workprocess.domain.adapter;

public class Product extends Context {
    private ProductId id;

    private String productName;

    public Product(String productName){
        this.productName = productName;
        this.id = ProductId.randomId(ProductId.class);
    }

    public ProductId getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                '}';
    }
}
