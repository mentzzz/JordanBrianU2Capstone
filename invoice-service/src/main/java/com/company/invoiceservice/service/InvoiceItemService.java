package com.company.invoiceservice.service;

import com.company.invoiceservice.dao.InvoiceItemDao;
import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class InvoiceItemService {
    InvoiceItemDao dao;

    @Autowired
    public InvoiceItemService(InvoiceItemDao dao) {
        this.dao = dao;
    }

    @Transactional
    public InvoiceItem saveInvoiceItem(InvoiceItem invoiceItem) {
        return dao.addInvoiceItem(invoiceItem);
    }

    public void updateInvoiceItem(InvoiceItem invoiceItem) {
        dao.updateInvoiceItem(invoiceItem);
    }

    public List<InvoiceItem> getAllInvoiceItems() {
        return dao.getAllInvoiceItems();
    }

    public InvoiceItem findInvoiceItem(int id) {
        return dao.getInvoiceItemById(id);
    }

    public void removeInvoiceItem(int id)
    {
        dao.deleteInvoiceItem(id);
    }


}
