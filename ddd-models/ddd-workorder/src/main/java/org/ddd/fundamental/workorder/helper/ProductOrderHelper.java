package org.ddd.fundamental.workorder.helper;

import org.ddd.fundamental.core.generator.Generators;
import org.ddd.fundamental.day.range.DateTimeRange;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.ddd.fundamental.workorder.enums.WorkOrderType;
import org.ddd.fundamental.workorder.value.OrderId;
import org.ddd.fundamental.workorder.value.ProductOrderValue;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ProductOrderHelper {


    public static ProductOrder createProductOrder(MaterialDTO materialDTO){
        DateTimeRange range = CollectionUtils.random(createDateTimeRanges());
        long days = range.days();
        ProductOrder productOrder =
                ProductOrder.create(
                        new ProductOrderValue.Builder(
                                materialDTO.id(),
                                CollectionUtils.random(productCount()),
                                LocalDate.now().plusDays(days+2)
                        ).withProductCode(materialDTO.getMaterialMaster().getCode())
                                .withProductName(materialDTO.getMaterialMaster().getName())
                                .withSaleOrderId(OrderId.randomId(OrderId.class))
                                .withOrganization("班组三组")
                                .withPlanStartTime(range.getStart())
                                .withPlanFinishTime(range.getEnd())
                                .build()
                );
        return productOrder;
    }

    public static List<ProductOrder> createProductOrders(List<MaterialDTO> products){
        List<ProductOrder> productOrders = new ArrayList<>();
        Generators.fill(productOrders,()->{
            MaterialDTO materialDTO = CollectionUtils.random(products);
            return createProductOrder(materialDTO);
        },5);
        return productOrders;
    }

    public static List<DateTimeRange> createDateTimeRanges(){
        return Arrays.asList(
                DateTimeRange.createStartAfterDuration(Instant.now(),10),
                DateTimeRange.createStartAfterDuration(Instant.now(),15),
                DateTimeRange.createStartAfterDuration(Instant.now(),18),
                DateTimeRange.createStartAfterDuration(Instant.now(),20),
                DateTimeRange.createStartAfterDuration(Instant.now(),30),
                DateTimeRange.createStartAfterDuration(Instant.now(),60)
        );
    }

    public static List<Double> productCount(){
        return Arrays.asList(
                12000.0,15000.0,25000.0,50000.0,8000.0,18000.0,100000.0
        );
    }

    public static List<WorkOrderType> workOrderTypes() {
        return Arrays.asList(WorkOrderType.values());
    }

    public static List<Integer> days() {
        return Arrays.asList(1,2,3,4,5,10,12,15);
    }

    public static List<Double> doubles(){
        return Arrays.asList(1000.0,1200.0,1500.0,1250.0,1100.0);
    }

    public static List<String> productCompanyNames() {
        return Arrays.asList("深圳市卓越科技有限公司","深圳市创新科技有限公司","深圳市梨子科技有限公司");
    }
}
