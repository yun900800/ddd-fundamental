package org.ddd.fundamental.shared.api.workorder;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.workorder.value.OrderId;
import org.ddd.fundamental.workorder.value.ProductOrderValue;

public class ProductOrderDTO extends AbstractDTO<OrderId> {

    private ProductOrderValue productOrder;

    @SuppressWarnings("unused")
    protected ProductOrderDTO(){
    }

    private ProductOrderDTO(ProductOrderValue productOrder,OrderId orderId){
        super(orderId);
        this.productOrder = productOrder;
    }

    public static ProductOrderDTO create(ProductOrderValue productOrder,
                                         OrderId orderId){
        return new ProductOrderDTO(productOrder,orderId);
    }

    @Override
    public OrderId id() {
        return super.id;
    }

    public ProductOrderValue getProductOrder() {
        return productOrder.clone();
    }
}
