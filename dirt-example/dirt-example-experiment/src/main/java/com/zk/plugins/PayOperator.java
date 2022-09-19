package com.zk.plugins;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PayOperator implements OrderOperatorPlugin {

    @Override
    public Optional<?> apply(OrderOperatorDTO operator) {
        //支付操作
        //doPay()
        return Optional.of("支付成功");
    }

    @Override
    public boolean supports(OrderOperatorDTO operatorDTO) {
        return operatorDTO.getOperatorType() == OrderOperatorType.PAY;
    }
}

