package com.company.adminservice.feign;

import com.company.adminservice.dto.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "invoice-service")
public interface InvoiceServiceClient {

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public InvoiceViewModel createInvoice(@RequestBody InvoiceViewModel invoiceViewModel);


//    @RequestMapping(value = "/invoices/id/{id}", method = RequestMethod.GET)
//    public InvoiceViewModel getInvoiceById(@PathVariable int id);

//    @RequestMapping(value = "/InvoiceItems", method = RequestMethod.GET)
//    public List<InvoiceItem> getAllInvoiceItemss();


}
