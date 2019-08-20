package com.company.retailservice.controller;

import com.company.retailservice.service.RetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RetailServiceController {

    @Autowired
    private RetailService service;

    @RequestMapping(value = "/retail/products", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseOrder getAllProducts() {
        return service.determineItemType(requestOrder);
    }
}
