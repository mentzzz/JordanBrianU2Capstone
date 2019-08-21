package com.company.invoiceservice.controller;

import com.company.invoiceservice.exception.NotFoundException;
import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.service.InvoiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/invoiceitems")
public class InvoiceItemController {
    @Autowired
    InvoiceItemService service;

    @PostMapping
    public InvoiceItem createInvoiceItem(@RequestBody InvoiceItem invoiceItem) {
        InvoiceItem invoiceItem1 = service.saveInvoiceItem(invoiceItem);
        return invoiceItem1;
    }

    @GetMapping(value = "/id/{id}")
    public InvoiceItem getInvoiceItem(@PathVariable int id) {
        InvoiceItem invoiceItem = service.findInvoiceItem(id);

        if (invoiceItem == null) {
            throw new NotFoundException("Invoice Item could not be retrieved for id " + id);
        }

        return invoiceItem;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getAllInvoiceItems() {
        return service.getAllInvoiceItems();
    }

    @PutMapping(value = "/products/id/{id}")
    public void updateInvoiceItem(@RequestBody InvoiceItem invoiceItem, @PathVariable int id) {
        if (id != invoiceItem.getId()) {
            throw new IllegalArgumentException("Invoice Item ID on path must match the ID in the invoice item object");
        }
        service.updateInvoiceItem(invoiceItem);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteInvoiceItem(@PathVariable(name = "id") int id) {
        service.removeInvoiceItem(id);
    }
}
