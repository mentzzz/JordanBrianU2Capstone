package com.company.retailservice.controller;

import com.company.retailservice.dto.OrderRequestView;
import com.company.retailservice.dto.OrderResponseView;
import com.company.retailservice.dto.Product;
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

    // *** Test route only ***
    @RequestMapping(value = "/retail/product/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Product getAllProducts(@PathVariable int id) {
        return service.getByProductId(id);
    }


    @RequestMapping(value = "/retail/order", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseView createOrder(@RequestBody OrderRequestView orderRequestView) {



        return null;
    }
}
