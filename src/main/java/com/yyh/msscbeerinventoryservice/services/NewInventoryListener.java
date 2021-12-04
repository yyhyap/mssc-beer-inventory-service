package com.yyh.msscbeerinventoryservice.services;

import com.yyh.brewery.model.events.BeerDto;
import com.yyh.brewery.model.events.NewInventoryEvent;
import com.yyh.msscbeerinventoryservice.config.JmsConfig;
import com.yyh.msscbeerinventoryservice.domain.BeerInventory;
import com.yyh.msscbeerinventoryservice.repositories.BeerInventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.transaction.Transactional;

@Slf4j
@Service
public class NewInventoryListener {

    @Autowired
    private BeerInventoryRepository beerInventoryRepository;

//    @Autowired
//    private ObjectMapper objectMapper;

    @Transactional
    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(@Payload NewInventoryEvent event, Message message) {
        log.debug("Got Inventory: " + event.toString());

//        NewInventoryEvent exampleEvent = null;
//
//        try {
//            String json = message.getBody(String.class);
//            log.debug("JSON for the message: {}", json);
//
//            exampleEvent = objectMapper.readValue(json, NewInventoryEvent.class);
//            log.debug("New NewInventoryEvent created for {}", exampleEvent.getBeerDto().getBeerName());
//        }catch (Exception e) {
//            e.printStackTrace();
//        }

        BeerDto beerDto = event.getBeerDto();

        beerInventoryRepository.save(BeerInventory.builder()
                        .beerId(beerDto.getId())
                        .upc(beerDto.getUpc())
                        .quantityOnHand(beerDto.getQuantityOnHand())
                        .build());
    }

}
