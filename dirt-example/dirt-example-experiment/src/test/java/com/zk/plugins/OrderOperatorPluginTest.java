package com.zk.plugins;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.Optional;


@SpringBootTest
public class OrderOperatorPluginTest {

    @Resource
    OrderService orderService;

    @Resource
    ApplicationContext applicationContext;

    @Test
    public void test_operation_closed() {
        final OrderOperatorDTO operator = new OrderOperatorDTO(OrderOperatorType.CLOSED);

        Optional<?> optionalO = orderService.operationOrder(operator);

        Assertions.assertEquals("关闭订单成功", optionalO.get());
    }


    @Test
    public void test_operation_pay() {
        final OrderOperatorDTO operator = new OrderOperatorDTO(OrderOperatorType.PAY);
        Optional<?> optionalO = orderService.operationOrder(operator);

        Assertions.assertEquals("支付成功", optionalO.get());
    }
}