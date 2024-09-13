package org.ddd.fundamental.repository.order;

import org.ddd.fundamental.repository.core.EntityModel;
import org.ddd.fundamental.repository.utils.MapUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class OrderItem extends EntityModel<Long> {

    private static Map<Long,String> products;
    private static Map<String,Long> reversedProducts;

    static {
        products = Map.of(1L,"香蕉",2L,"橘子",
                3L,"苹果", 4L,"库尔勒香梨");
        reversedProducts = MapUtils.reverseMap(products);
        ProductInfo.loadProductInfo();
    }

    private static final String DEFAULT_DESC = "这是好吃点为您推荐的优质商品";
    private static final int DEFAULT_QUANTITY = 1;

    /**
     * 订单项目的数量
     */
    private int quantity;

    /**
     * 订单项描述说明
     */
    private String description;


    /**
     * 产品信息
     */
    private ProductInfo productInfo;

    private OrderItem(String productName, int quantity, String description) {
        this(-1L);
        this.description = description;
        this.quantity = quantity;
        this.productInfo = ProductInfo.getProductId(reversedProducts.get(productName));
    }

    public static OrderItem create(String productName) {
        return new OrderItem(productName, DEFAULT_QUANTITY, DEFAULT_DESC);
    }

    public static OrderItem create(String productName, int quantity, String description) {
        return new OrderItem(productName,quantity, description);
    }

    public OrderItem(Long id) {
        super(id);
    }

    /**
     * 修改订单项的数量
     * @param quantity
     * @return
     */
    public OrderItem changeQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * 获取产品的总价, 单价乘以数量
     * @return
     */
    public BigDecimal itemPrice() {
        return getItemAmount().multiply(BigDecimal.valueOf(getQuantity()));
    }

    public String getProductName() {
        return productInfo.getProductName();
    }

    public BigDecimal getItemAmount() {
        return productInfo.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public Long getProductId() {
        return productInfo.getId();
    }

    public String dirtyKey(){
        return productInfo.getProductName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(productInfo.getProductName(),orderItem.productInfo.getProductName())
                && Objects.equals(productInfo.getId(), orderItem.productInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productInfo.getProductName(), productInfo.getId());
    }
}
