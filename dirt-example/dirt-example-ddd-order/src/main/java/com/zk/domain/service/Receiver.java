package com.zk.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.domain.core.vo.IntentionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "intention")
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void receiveMessage(String message) {
        LOGGER.info("Received new intention <" + message + ">");
        try {
//            String[] values = message.split("\\|");
//            if (values.length == 6) {
//                IntentionVo intentionVo =
//                        new IntentionVo(values[0],
//                                Double.parseDouble(values[1]),
//                                Double.parseDouble(values[2]),
//                                Double.parseDouble(values[3]),
//                                Double.parseDouble(values[4]),
//                                values[5],
//                                2000L);
//
//                orderService.put(intentionVo);
//            }
            IntentionVo vo = mapper.readValue(message, IntentionVo.class);
            orderService.createOrder(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receivePositionUpdate(String message) {
        LOGGER.info("Received position update " + message);
        try {
            String[] values = message.split("\\|");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
