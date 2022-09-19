package com.zk.plugins;

import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class OrderService   {

    @Resource
    PluginRegistry<OrderOperatorPlugin, OrderOperatorDTO> orderOperatorPluginRegistry;

    public Optional<?> operationOrder(OrderOperatorDTO operator) {
        Optional<OrderOperatorPlugin> pluginFor = orderOperatorPluginRegistry.getPluginFor(operator);
        return pluginFor.get().apply(operator);
    }
}
