package com.company.adminservice.service;

import com.company.adminservice.dto.Customer;
import com.company.adminservice.dto.Product;
import com.company.adminservice.feign.CustomerServiceClient;
import com.company.adminservice.feign.ProductServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceLayer {

    @Autowired
    private ProductServiceClient productServiceClient;


    // CREATE PRODUCT
    public Product createCustomer(Product product) {
        // use feign to create a new customer
        product = productServiceClient.createProduct(product);
        return product;
    }


    // FIND PRODUCT BY ID
    public Product findProduct(int id) {
        // use feign to find a post
        Product product = productServiceClient.getProductById(id);
        return product;
    }

    // FINDALL PRODUCT
    public List<Product> findAllProducts() {
        // use feign to find all post
        List<Product> product = productServiceClient.getAllProducts();
        return product;
    }

    // UPDATE PRODUCT
    public void updateProduct(int id, Product product) {
        // use feign to update a new post
        productServiceClient.updateProduct(id, product);
    }


    // DELETE PRODUCT BY ID
    public void deleteProduct(int id) {
        // use feign to delete a post
        productServiceClient.deleteProduct(id);
    }


}
