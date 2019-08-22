package com.company.adminservice.controller;

import com.company.adminservice.dto.Invoice;
import com.company.adminservice.dto.InvoiceItem;
import com.company.adminservice.service.InvoiceServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceController {


    @Autowired
    private InvoiceServiceLayer service;


    @RequestMapping(value = "/administration/invoices", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        invoice = service.createInvoice(invoice);
        return invoice;
    }

    @RequestMapping(value = "/administration/invoices", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoice = service.findAllInvoices();
        return invoice;
    }

    @RequestMapping(value = "/administration/invoices/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoiceById(@PathVariable("id") int id) {
        Invoice invoice = service.findInvoice(id);
        return invoice;
    }

    @RequestMapping(value = "/administration/invoices/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@PathVariable("id") int id, @RequestBody Invoice invoice) {
        service.updateInvoice(id, invoice);
    }

    @RequestMapping(value = "/administration/invoices/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable(name = "id") int id) {
        service.deleteInvoice(id);
    }





    @RequestMapping(value = "/administration/invoiceitem", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceItem createInvoiceItem(@RequestBody InvoiceItem invoiceItem) {
        invoiceItem = service.createInvoiceItem(invoiceItem);
        return invoiceItem;
    }

    @RequestMapping(value = "/administration/invoiceitem", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getAllInvoiceItems() {
        List<InvoiceItem> invoiceItem = service.findAllInvoiceItems();
        return invoiceItem;
    }

    @RequestMapping(value = "/administration/invoiceitem/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceItem getInvoiceItemById(@PathVariable("id") int id) {
        InvoiceItem invoiceItem = service.findInvoiceItem(id);
        return invoiceItem;
    }

    @RequestMapping(value = "/administration/invoiceitem/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoiceItem(@PathVariable("id") int id, @RequestBody InvoiceItem invoiceItem) {
        service.updateInvoiceItem(id, invoiceItem);
    }

    @RequestMapping(value = "/administration/invoiceitem/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoiceItem(@PathVariable(name = "id") int id) {
        service.deleteInvoiceItem(id);
    }

}
