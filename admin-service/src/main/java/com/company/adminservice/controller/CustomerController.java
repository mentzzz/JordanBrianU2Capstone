package com.company.adminservice.controller;

import com.company.adminservice.dto.Customer;
import com.company.adminservice.service.CustomerServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerServiceLayer service;


    @RequestMapping(value = "/administration/customers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer) {
        customer = service.createCustomer(customer);
        return customer;
    }




}
