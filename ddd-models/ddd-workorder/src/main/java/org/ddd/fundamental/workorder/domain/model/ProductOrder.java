package org.ddd.fundamental.workorder.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.workorder.enums.OrderStatus;
import org.ddd.fundamental.workorder.enums.Urgency;
import org.ddd.fundamental.workorder.value.OrderId;
import org.ddd.fundamental.workorder.value.ProductOrderValue;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "w_product_order")
@DynamicUpdate
public class ProductOrder extends AbstractAggregateRoot<OrderId> {

    private ProductOrderValue productOrder;

    @SuppressWarnings("unused")
    protected ProductOrder(){
    }

    private ProductOrder(ProductOrderValue productOrder){
        super(OrderId.randomId(OrderId.class));
        this.productOrder = productOrder;
    }

    public static ProductOrder create(){
        return new ProductOrder();
    }

    public static ProductOrder create(ProductOrderValue productOrder){
        return new ProductOrder(productOrder);
    }

    public ProductOrderValue getProductOrder() {
        return productOrder.clone();
    }

    public ProductOrder changeProductOrder(ProductOrderValue productOrder){
        this.productOrder = productOrder;
        return this;
    }

    public ProductOrder changeStatus(OrderStatus orderStatus){
        //changeUpdated();
        this.productOrder.changeStatus(orderStatus);
        return this;
    }

    public ProductOrder changeUrgency(Urgency urgency){
        changeUpdated();
        this.productOrder.changeUrgency(urgency);
        return this;
    }

    public ProductOrder configOrganization(String organization){
        changeUpdated();
        this.productOrder.configOrganization(organization);
        return this;
    }

    public ProductOrder configSaleOrderId(OrderId saleOrderId){
        changeUpdated();
        this.productOrder.configSaleOrderId(saleOrderId);
        return this;
    }

    public ProductOrder changePlanStartTime(Instant planStartTime){
        changeUpdated();
        this.productOrder.changePlanStartTime(planStartTime);
        return this;
    }

    public ProductOrder changePlanFinishTime(Instant planFinishTime){
        changeUpdated();
        this.productOrder.changePlanFinishTime(planFinishTime);
        return this;
    }

    @Override
    public long created() {
        return 0;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "productOrder=" + productOrder +
                '}';
    }
}
