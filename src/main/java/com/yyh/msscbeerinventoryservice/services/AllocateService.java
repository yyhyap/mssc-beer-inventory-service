package com.yyh.msscbeerinventoryservice.services;

import com.yyh.brewery.model.BeerOrderDto;

public interface AllocateService {
    Boolean allocateOrder(BeerOrderDto beerOrderDto);
}
