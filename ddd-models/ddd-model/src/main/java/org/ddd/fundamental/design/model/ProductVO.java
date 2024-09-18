package org.ddd.fundamental.design.model;

import lombok.Builder;
import lombok.Data;
import org.ddd.fundamental.constants.ErrorCode;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Builder
public class ProductVO {
    /**
     * 商品SKU，唯一
     */
    private Long skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品图片路径
     */
    private String imgPath;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存
     */
    private Integer stock;

    public Result illegalPrice() {
        if (getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return Result.failure(ErrorCode.PARAM_PRICE_ILLEGAL_ERROR);
        }
        return Result.success();
    }

    public Result illegalStock(){
        if(getStock() < 0){
            return Result.failure(ErrorCode.PARAM_STOCK_ILLEGAL_ERROR);
        }
        return Result.success();
    }

    public Result nullValueCheck(){
        //参数必填校验
        if (Objects.isNull(this)) {
            return Result.failure(ErrorCode.PARAM_NULL_ERROR);
        }
        //SkuId商品主键参数必填校验
        if (Objects.isNull(this.getSkuId())) {
            return Result.failure(ErrorCode.PARAM_SKU_NULL_ERROR);
        }
        //Price价格参数必填校验
        if (Objects.isNull(this.getPrice())) {
            return Result.failure(ErrorCode.PARAM_PRICE_NULL_ERROR);
        }
        //Stock库存参数必填校验
        if (Objects.isNull(this.getStock())) {
            return Result.failure(ErrorCode.PARAM_STOCK_NULL_ERROR);
        }
        return Result.success();
    }
}
