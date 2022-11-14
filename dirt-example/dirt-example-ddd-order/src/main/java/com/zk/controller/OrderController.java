package com.zk.controller;


import com.zk.domain.core.vo.Events;
import com.zk.domain.core.vo.StateRequest;
import com.zk.domain.exception.OrderRuntimeException;
import com.zk.domain.repository.OrderRepository;
import com.zk.domain.service.FsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController {
    @Autowired
    FsmService fsmService;
    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/order/cancel")
    public List<String> cancelOrder(Long driverId, Long orderId) {
        StateRequest stateRequest = new StateRequest();
        stateRequest.setEvent(Events.CANCEL);
        stateRequest.setData(orderRepository.findById(orderId).get());
        stateRequest.setUId(UUID.randomUUID().toString());
        stateRequest.setOrderId(orderId);
        stateRequest.setUserId(driverId);
        try {
            fsmService.changeState(stateRequest);
            return Arrays.asList("success", "");
        } catch (OrderRuntimeException oe) {
            return Arrays.asList(oe.getErrorCode(), oe.getErrorMessage());
        }
    }

    @PostMapping("/order/aboard")
    public List<String> aboard(Long driverId, Long orderId) {
        StateRequest stateRequest = new StateRequest();
        stateRequest.setEvent(Events.ABOARD);
        stateRequest.setData(orderRepository.findById(orderId).get());
        stateRequest.setUId(UUID.randomUUID().toString());
        stateRequest.setOrderId(orderId);
        stateRequest.setUserId(driverId);
        try {
            fsmService.changeState(stateRequest);
            return Arrays.asList("success", "");
        } catch (OrderRuntimeException oe) {
            return Arrays.asList(oe.getErrorCode(), oe.getErrorMessage());
        }
    }
}
