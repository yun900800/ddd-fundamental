package org.ddd.fundamental.workorder.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.workorder.enums.OrderStatus;
import org.ddd.fundamental.workorder.enums.Urgency;
import org.ddd.fundamental.workorder.value.OrderId;
import org.ddd.fundamental.workorder.value.ProductOrderValue;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "w_product_order")
public class ProductOrder extends AbstractAggregateRoot<OrderId> {

    private ProductOrderValue productOrder;

    @SuppressWarnings("unused")
    protected ProductOrder(){
    }

    private ProductOrder(ProductOrderValue productOrder){
        super(OrderId.randomId(OrderId.class));
        this.productOrder = productOrder;
    }

    public static ProductOrder create(ProductOrderValue productOrder){
        return new ProductOrder(productOrder);
    }

    public ProductOrderValue getProductOrder() {
        return productOrder.clone();
    }

    public ProductOrder changeStatus(OrderStatus orderStatus){
        this.productOrder.changeStatus(orderStatus);
        return this;
    }

    public ProductOrder changeUrgency(Urgency urgency){
        this.productOrder.changeUrgency(urgency);
        return this;
    }

    public ProductOrder configOrganization(String organization){
        this.productOrder.configOrganization(organization);
        return this;
    }

    public ProductOrder configSaleOrderId(OrderId saleOrderId){
        this.productOrder.configSaleOrderId(saleOrderId);
        return this;
    }

    public ProductOrder changePlanStartTime(Instant planStartTime){
        this.productOrder.changePlanStartTime(planStartTime);
        return this;
    }

    public ProductOrder changePlanFinishTime(Instant planFinishTime){
        this.productOrder.changePlanFinishTime(planFinishTime);
        return this;
    }

    @Override
    public long created() {
        return 0;
    }
}
