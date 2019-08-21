package com.company.invoiceservice.controller;

import com.company.invoiceservice.dao.InvoiceDao;
import com.company.invoiceservice.dao.InvoiceItemDao;
import com.company.invoiceservice.exception.NotFoundException;
import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.service.InvoiceService;
import com.company.invoiceservice.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    InvoiceService service;

    @PostMapping
    public InvoiceViewModel createInvoice(@RequestBody InvoiceViewModel invoiceViewModel) {
        return service.saveInvoice(invoiceViewModel);
    }

    @GetMapping(value = "/id/{id}")
    public InvoiceViewModel getInvoice(@PathVariable int id) {
        InvoiceViewModel invoiceViewModel = service.findInvoice(id);
        if (invoiceViewModel == null)
            throw new NotFoundException("Invoice could not be retrieved");
        return invoiceViewModel;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices() {
        return service.getAllInvoices();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable int id) {
        service.removeInvoice(id);
    }

    @PutMapping(value = "/id/{id}")
    public void updateInvoice(@RequestBody InvoiceViewModel invoice, @PathVariable int id) {
        if (id != invoice.getInvoiceId()) {
            throw new IllegalArgumentException("Invoice ID on path must match the ID in the invoice object");
        }

        service.updateInvoice(invoice);
    }
}
