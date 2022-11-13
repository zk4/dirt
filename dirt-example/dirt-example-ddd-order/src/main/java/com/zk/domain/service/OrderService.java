package com.zk.domain.service;


import com.zk.dirt.conf.DirtSnowflake;
import com.zk.domain.core.root.Order;
import com.zk.domain.core.vo.CustomerVo;
import com.zk.domain.core.vo.DriverVo;
import com.zk.domain.core.vo.FlowState;
import com.zk.domain.core.vo.IntentionVo;
import com.zk.domain.exception.OrderRuntimeException;
import com.zk.domain.repository.OrderRepository;
import com.zk.infrastructure.UserRibbonHystrixApi;
import com.zk.util.SequenceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {
    public static final String UUID_KEY = "UUID_KEY";
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    UserRibbonHystrixApi userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SequenceFactory sequenceFactory;


    private Long generateOrderId() {
        //return "T" + String.format("%010d", sequenceFactory.generate("order"));
        return new DirtSnowflake().nextId();
    }


    @Transactional
    public Order createOrder(IntentionVo intention) {
        //在调用远程user服务获取用户信息的时候，必须有熔断，否则在事务中很危险
        Order order = new Order();
        order.setId(generateOrderId());
        CustomerVo customerVo = userService.findCustomerById(intention.getCustomerId());
        DriverVo driverVo = userService.findDriverById(intention.getDriverId());
        order.setCustomer(customerVo);
        order.setDriver(driverVo);
        order.setOrderStatus(FlowState.WAITING_ABOARD.toValue());
        order.setOpened(new Date());
        order.setStartLong(intention.getStartLong());
        order.setStartLat(intention.getStartLat());
        order.setDestLong(intention.getDestLong());
        order.setDestLat(intention.getDestLat());
        order.setIntentionId(intention.getIntentionId());
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public void aboard(Order order) {
        order.setOrderStatus(FlowState.WAITING_ARRIVE.toValue());
        order.setOpened(new Date());
        orderRepository.save(order);
    }

    @Transactional
    public void cancel(Order order) {
        Date currentTime = new Date();
        if ((currentTime.getTime() - order.getOpened().getTime()) <= 3 * 60 * 1000L) {
            order.setOrderStatus(FlowState.CANCELED.toValue());
            orderRepository.save(order);
        } else {
            throw new OrderRuntimeException("040001");
        }
    }


}
