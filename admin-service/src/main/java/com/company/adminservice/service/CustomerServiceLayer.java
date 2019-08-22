package com.company.adminservice.service;

import com.company.adminservice.dto.Customer;
import com.company.adminservice.feign.CustomerServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerServiceLayer {

    @Autowired
    private CustomerServiceClient customerServiceClient;



    // CREATE CUSTOMER
    public Customer createCustomer(Customer customer) {
        // use feign to create a new customer
        customer = customerServiceClient.createCustomer(customer);
        return customer;
    }


    // FIND CUSTOMER BY ID
    public Customer findCustomer(int id) {
        // use feign to find a post
        Customer customer = customerServiceClient.getCustomerById(id);
        return customer;
    }

    // FINDALL CUSTOMER
    public List<Customer> findAllCustomers() {
        // use feign to find all post
        List<Customer> customer = customerServiceClient.getAllCustomers();
        return customer;
    }

    // UPDATE CUSTOMER
    public void updateCustomer(int id, Customer customer) {
        // use feign to update a new post
        customerServiceClient.updateCustomer(id, customer);
    }

    // DELETE CUSTOMER BY ID
    public void deleteCustomer(int id) {
        // use feign to delete a post
        customerServiceClient.deleteCustomer(id);
    }




















}
