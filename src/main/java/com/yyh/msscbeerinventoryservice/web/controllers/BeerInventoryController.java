package com.yyh.msscbeerinventoryservice.web.controllers;


import com.yyh.msscbeerinventoryservice.repositories.BeerInventoryRepository;
import com.yyh.msscbeerinventoryservice.web.mappers.BeerInventoryMapper;
import com.yyh.brewery.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerInventoryController {

    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerInventoryMapper beerInventoryMapper;

    @GetMapping("api/v1/beer/{beerId}/inventory")
    List<BeerInventoryDto> listBeersById(@PathVariable UUID beerId){
        log.debug("Finding Inventory for beerId:" + beerId);

        return beerInventoryRepository.findAllByBeerId(beerId)
                .stream()
                /*
                 * double colon operator, method reference operator
                 * <Class name>::<method name>
                 * map all beerInventory in List<BeerInventory> (from beerInventoryRepository) to BeerInventoryDto
                 * beerInventory -> beerInventoryMapper.beerInventoryToBeerInventoryDto(beerInventory)
                 * */
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDto)
                .collect(Collectors.toList());
    }
}
