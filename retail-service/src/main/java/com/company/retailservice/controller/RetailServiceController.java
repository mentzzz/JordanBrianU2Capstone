package com.company.retailservice.controller;

import com.company.retailservice.dto.OrderRequestView;
import com.company.retailservice.dto.OrderResponseView;
import com.company.retailservice.dto.SearchResponseView;
import com.company.retailservice.service.RetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RetailServiceController {

    @Autowired
    private RetailService service;


    // Retail-Service Controller Route Methods:

    @RequestMapping(value = "/retail/products", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public SearchResponseView getAllProducts() {
        return service.getAllProducts();
    }


    @RequestMapping(value = "/retail/order", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseView createOrder(@RequestBody OrderRequestView orderRequestView) {



        return null;
    }
}
