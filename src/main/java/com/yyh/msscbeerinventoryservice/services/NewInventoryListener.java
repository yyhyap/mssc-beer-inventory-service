package com.yyh.msscbeerinventoryservice.services;

import com.yyh.common.events.BeerDto;
import com.yyh.common.events.NewInventoryEvent;
import com.yyh.msscbeerinventoryservice.config.JmsConfig;
import com.yyh.msscbeerinventoryservice.domain.BeerInventory;
import com.yyh.msscbeerinventoryservice.repositories.BeerInventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NewInventoryListener {

    @Autowired
    private BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent event) {
        log.debug("Got Inventory: " + event.toString());

        BeerDto beerDto = event.getBeerDto();

        beerInventoryRepository.save(BeerInventory.builder()
                        .beerId(beerDto.getId())
                        .upc(beerDto.getUpc())
                        .quantityOnHand(beerDto.getQuantityOnHand())
                        .build());
    }

}
