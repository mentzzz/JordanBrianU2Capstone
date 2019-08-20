package com.company.retailservice.service;

import com.company.retailservice.dto.Product;
import com.company.retailservice.dto.SearchResponseView;
import com.company.retailservice.feign.ProductServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RetailService {


    // Dependencies

    @Autowired
    private ProductServiceClient productServiceClient;


    // constructor

    // Service Layer public methods:

    public SearchResponseView getAllProducts() {

        SearchResponseView returnList = new SearchResponseView();

        returnList.setProductList(productServiceClient.getAllProducts());

        return returnList;
    }




}
