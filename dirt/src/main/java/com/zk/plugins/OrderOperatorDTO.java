package com.zk.plugins;

public class OrderOperatorDTO {

    final OrderOperatorType type;
    public OrderOperatorDTO(OrderOperatorType type) {
        this.type = type;
    }

    public OrderOperatorType getOperatorType() {
        return this.type;
    }
}
