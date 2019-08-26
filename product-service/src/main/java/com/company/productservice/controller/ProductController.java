package com.company.productservice.controller;

import com.company.productservice.dao.ProductDao;
import com.company.productservice.exception.NotFoundException;
import com.company.productservice.model.Product;
import com.company.productservice.service.ProductService;
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
@RequestMapping(value = "/products")
@RefreshScope
@CacheConfig(cacheNames = "products")
public class ProductController {
    @Autowired
    ProductService service;

    @CachePut(key = "#product.getId()")
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        Product product1 = service.saveProduct(product);
        return product1;
    }

    @Cacheable
    @GetMapping(value = "/id/{id}")
    public Product getProduct(@PathVariable int id) {
        Product product = service.getProduct(id);

        if (product == null) {
            throw new NotFoundException("Product could not be retrieved for id " + id);
        }

        return product;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @CacheEvict(key = "#product.getId()")
    @PutMapping(value = "/id/{id}")
    public void updateProduct(@RequestBody Product product, @PathVariable int id) {
        if (id != product.getId()) {
            throw new IllegalArgumentException("Product ID on path must match the ID in the product object");
        }

        service.updateProduct(product);
    }

    @CacheEvict
    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable(name = "id") int id) {
        service.removeProduct(id);
    }

}
