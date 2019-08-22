package com.company.adminservice.feign;

import com.company.adminservice.dto.Invoice;
import com.company.adminservice.dto.InvoiceItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceServiceClient {

    // INVOICE ROUTES

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public Invoice createInvoice(@RequestBody Invoice invoice);

    @RequestMapping(value = "/invoices/id/{id}", method = RequestMethod.GET)
    public Invoice getInvoiceById(@PathVariable int id);

    @RequestMapping(value = "/Invoices", method = RequestMethod.GET)
    public List<Invoice> getAllInvoices();

    @RequestMapping(value = "/invoices/id/{id}", method = RequestMethod.PUT)
    public void updateInvoice(@PathVariable int id, @RequestBody Invoice invoice);

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.DELETE)
    public void deleteInvoice(@PathVariable int id);


    // INVOICE ITEM ROUTES


    @RequestMapping(value = "/invoices/invoiceitems", method = RequestMethod.POST)
    public InvoiceItem createInvoiceItem(@RequestBody InvoiceItem invoiceItem);

    @RequestMapping(value = "/invoices/invoiceitems/id/{id}", method = RequestMethod.GET)
    public InvoiceItem getInvoiceItemById(@PathVariable int id);

    @RequestMapping(value = "/invoices/invoiceitems", method = RequestMethod.GET)
    public List<InvoiceItem> getAllInvoiceItems();

    @RequestMapping(value = "/invoices/invoiceitems/id/{id}", method = RequestMethod.PUT)
    public void updateInvoiceItem(@PathVariable int id, @RequestBody InvoiceItem invoiceItem);

    @RequestMapping(value = "/invoices/invoiceitems/{id}", method = RequestMethod.DELETE)
    public void deleteInvoiceItem(@PathVariable int id);






}
