package org.ddd.fundamental.repository.order;

import org.ddd.fundamental.repository.core.EntityModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽象出一个产品对象出来
 */
public class ProductInfo extends EntityModel<Long> {

    private static Map<Long,ProductInfo> products = new HashMap<>();

    public ProductInfo(Long aLong) {
        super(aLong);
    }

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品单价
     */
    private BigDecimal price;

    public static void loadProductInfo() {
        new ProductInfo(1L,"香蕉", BigDecimal.valueOf(4.5)).store();
        new ProductInfo(2L,"橘子", BigDecimal.valueOf(2.8)).store();
        new ProductInfo(3L,"苹果", BigDecimal.valueOf(3.6)).store();
        new ProductInfo(4L,"库尔勒香梨", BigDecimal.valueOf(7.6)).store();
    }

    public static ProductInfo getProductId(Long productId) {
        return products.get(productId);
    }

    private ProductInfo(Long id,String productName, BigDecimal price){
        this(id);
        this.productName = productName;
        this.price = price;
    }

    private void store() {
        if (!products.containsKey(this.getId())) {
            products.put(this.getId(),this);
        }
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
