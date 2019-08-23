package com.company.retailservice.feign;

import com.company.retailservice.dto.Invoice;
import com.company.retailservice.dto.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceServiceClient {

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public InvoiceViewModel createInvoice(@RequestBody InvoiceViewModel invoiceViewModel);

    @RequestMapping(value = "/invoices/id/{id}", method = RequestMethod.GET)
    public InvoiceViewModel getInvoiceById(@PathVariable int id);

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<InvoiceViewModel> getAllInvoices();

    @RequestMapping(value = "/invoices/id/{id}", method = RequestMethod.PUT)
    public void updateInvoice(@PathVariable int id, @RequestBody InvoiceViewModel invoiceViewModel);

    @RequestMapping(value = "/invoices/id/{id}", method = RequestMethod.DELETE)
    public void deleteInvoice(@PathVariable int id);


//    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
//    public InvoiceViewModel createInvoice(@RequestBody InvoiceViewModel invoiceViewModel);
//
//
////    @RequestMapping(value = "/invoices/id/{id}", method = RequestMethod.GET)
////    public InvoiceViewModel getInvoiceById(@PathVariable int id);
//
////    @RequestMapping(value = "/InvoiceItems", method = RequestMethod.GET)
////    public List<InvoiceItem> getAllInvoiceItemss();


}
