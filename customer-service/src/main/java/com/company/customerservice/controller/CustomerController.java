package com.company.customerservice.controller;

import com.company.customerservice.dao.CustomerDao;
import com.company.customerservice.exception.NotFoundException;
import com.company.customerservice.model.Customer;
import com.company.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
@RefreshScope
@CacheConfig(cacheNames = {"customers"})
public class CustomerController {
    @Autowired
    CustomerService service;

    @CachePut(key = "#customers.getId()")
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        Customer customer1 = service.saveCustomer(customer);
        return customer1;
    }

    @Cacheable
    @GetMapping(value = "/id/{id}")
    public Customer getCustomer(@PathVariable("id") int id) {
        Customer customer = service.getCustomer(id);

        if (customer == null) {
            throw new NotFoundException("Customer could not be retrieved for id " + id);
        }

        return customer;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @CacheEvict(key = "#customers.getId()")
    @PutMapping(value = "/id/{id}")
    public void updateCustomer(@RequestBody Customer customer, @PathVariable int id) {
        if (id != customer.getId()) {
            throw new IllegalArgumentException("Customer ID on path must match the ID in the customer object");
        }

        service.updateCustomer(customer);
    }

    @CacheEvict
    @DeleteMapping(value = "/{id}")
    public void deleteCustomer(@PathVariable(name = "id") int id) {
        service.deleteCustomer(id);
    }
}
