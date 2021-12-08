package com.yyh.msscbeerinventoryservice.services;

import com.yyh.brewery.model.events.AllocateOrderRequest;
import com.yyh.brewery.model.events.AllocateOrderResult;
import com.yyh.msscbeerinventoryservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AllocationListener {

    @Autowired
    private AllocateService allocateService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest request) {
        AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();

        builder.beerOrderDto(request.getBeerOrderDto());

        try {
            Boolean allocationResult = allocateService.allocateOrder(request.getBeerOrderDto());

            // if fully allocated, pending inventory equal to false
            if(allocationResult) {
                builder.allocationError(false);
                builder.pendingInventory(false);
            } else {
                builder.allocationError(false);
                builder.pendingInventory(true);
            }
        } catch (Exception e) {
            log.error("Allocation failed for Order Id:" + request.getBeerOrderDto().getId());
            builder.allocationError(true);
        }

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, builder.build());
    }
}
