package com.yyh.msscbeerinventoryservice.services;

import com.yyh.brewery.model.events.DeallocateOrderRequest;
import com.yyh.msscbeerinventoryservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeallocationListener {

    @Autowired
    private AllocateService allocateService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateOrderRequest request) {
        allocateService.deallocateOrder(request.getBeerOrderDto());
    }
}
