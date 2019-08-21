package com.company.retailservice.feign;

import com.company.retailservice.dto.Invoice;
import com.company.retailservice.dto.InvoiceItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InvoiceServiceClient {


    @RequestMapping(value = "/inventorys", method = RequestMethod.GET)
    public List<Invoice> getAllInvoices();

    @RequestMapping(value = "/InvoiceItems", method = RequestMethod.GET)
    public List<InvoiceItem> getAllInvoiceItemss();


}
