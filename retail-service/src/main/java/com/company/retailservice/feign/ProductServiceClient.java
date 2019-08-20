package com.company.retailservice.feign;

import com.company.retailservice.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceClient {



    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> getAllProducts();

//    @RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
//    public Comment getCommentById(@PathVariable int id);


}
