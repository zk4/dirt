package com.zk.plugins;

import org.springframework.plugin.core.Plugin;

import java.util.Optional;

public interface OrderOperatorPlugin extends Plugin<OrderOperatorDTO> {

    /**
     * 定义操作动作
     * @param operator
     * @return
     */
    public Optional<?> apply(OrderOperatorDTO operator);
}
