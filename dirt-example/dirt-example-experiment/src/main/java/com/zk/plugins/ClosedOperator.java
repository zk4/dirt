package com.zk.plugins;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClosedOperator implements OrderOperatorPlugin {

    @Override
    public Optional<?> apply(OrderOperatorDTO operator) {
        //关闭操作
        //doClosed()
        return Optional.of("关闭订单成功");
    }

    @Override
    public boolean supports(OrderOperatorDTO operatorDTO) {
        return operatorDTO.getOperatorType() == OrderOperatorType.CLOSED;
    }
}