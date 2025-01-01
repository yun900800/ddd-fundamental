package org.ddd.fundamental.shared.api.workorder;

import org.ddd.fundamental.day.range.DateTimeRange;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.workorder.value.OrderId;

public class ProductOrderRequest {

    private MaterialDTO materialDTO;

    private OrderId saleOrderId;

    private double productCount;

    private String organization;

    private DateTimeRange dateTimeRange;

    @SuppressWarnings("unused")
    protected ProductOrderRequest(){}

    private ProductOrderRequest(MaterialDTO materialDTO,
                                OrderId saleOrderId,
                                double productCount,
                                String organization,
                                DateTimeRange dateTimeRange){
        this.materialDTO = materialDTO;
        this.saleOrderId = saleOrderId;
        this.productCount = productCount;
        this.organization = organization;
        this.dateTimeRange = dateTimeRange;
    }

    public static ProductOrderRequest create(MaterialDTO materialDTO,
                                             OrderId saleOrderId,
                                             double productCount,
                                             String organization,
                                             DateTimeRange dateTimeRange){
        return new ProductOrderRequest(materialDTO,saleOrderId,productCount,organization,dateTimeRange);
    }

    public MaterialDTO getMaterialDTO() {
        return materialDTO;
    }

    public OrderId getSaleOrderId() {
        return saleOrderId;
    }

    public double getProductCount() {
        return productCount;
    }

    public String getOrganization() {
        return organization;
    }

    public DateTimeRange getDateTimeRange() {
        return dateTimeRange;
    }

    @Override
    public String toString() {
        return "ProductOrderRequest{" +
                "materialDTO=" + materialDTO +
                ", saleOrderId=" + saleOrderId +
                ", productCount=" + productCount +
                ", organization='" + organization + '\'' +
                ", dateTimeRange=" + dateTimeRange +
                '}';
    }
}
