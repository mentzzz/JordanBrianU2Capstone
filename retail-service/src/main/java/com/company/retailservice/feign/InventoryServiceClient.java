package com.company.retailservice.feign;

import com.company.retailservice.dto.Invoice;
import com.company.retailservice.dto.InvoiceItem;
import com.company.retailservice.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {



    @RequestMapping(value = "/inventory/{inventory}", method = RequestMethod.GET)
    public int getQuantity(@PathVariable int inventory);




}
