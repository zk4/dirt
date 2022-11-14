//package com.zk.intention.third.api;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//
//@FeignClient("ddd-uc")
//public interface UcFeignService {
//
//    /**
//     * 根据skuId查询sku信息
//     * @param skuId
//     * @return
//     */
//    @RequestMapping("/product/skuinfo/info/{skuId}")
//    R getInfo(@PathVariable("skuId") Long skuId);
//
//    /**
//     * 根据skuId查询pms_sku_sale_attr_value表中的信息
//     * @param skuId
//     * @return
//     */
//    @GetMapping(value = "/product/skusaleattrvalue/stringList/{skuId}")
//    List<String> getSkuSaleAttrValues(@PathVariable("skuId") Long skuId);
//
//    /**
//     * 根据skuId查询当前商品的最新价格
//     * @param skuId
//     * @return
//     */
//    @GetMapping(value = "/product/skuinfo/{skuId}/price")
//    BigDecimal getPrice(@PathVariable("skuId") Long skuId);
//}
