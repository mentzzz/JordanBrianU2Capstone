package com.company.invoiceservice.service;

import com.company.invoiceservice.dao.InvoiceDao;
import com.company.invoiceservice.dao.InvoiceItemDao;
import com.company.invoiceservice.model.Customer;
import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceService {
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;

    @Autowired
    public InvoiceService(InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao) {
        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
    }

    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel invoiceViewModel) {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(invoiceViewModel.getCustomerId());
        invoice.setPurchaseDate(invoiceViewModel.getPurchaseDate());
        invoice = invoiceDao.addInvoice(invoice);
        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());

        List<InvoiceItem> invoiceItems = invoiceViewModel.getInvoiceItems();
        invoiceItems.stream().forEach(invoiceItem -> {
            invoiceItem.setInvoiceId(invoiceViewModel.getInvoiceId());
            invoiceItemDao.addInvoiceItem(invoiceItem);
        });

        invoiceItems = invoiceItemDao.getInvoiceItemsByInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setInvoiceItems(invoiceItems);

        return invoiceViewModel;
    }

    public InvoiceViewModel findInvoice(int id) {
        Invoice invoice = invoiceDao.getInvoiceById(id);
        if(invoice == null )
            return null;
        else
            return buildInvoiceViewModel(invoice);
    }

    public void removeInvoice(int id)
    {
        List<InvoiceItem> invoiceItems = invoiceItemDao.getInvoiceItemsByInvoiceId(id);
        for (InvoiceItem invoiceitem: invoiceItems) {
            invoiceItemDao.deleteInvoiceItem(invoiceitem.getId());
        }
        invoiceDao.deleteInvoice(id);
    }

    public void updateInvoice(InvoiceViewModel invoiceViewModel) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invoiceViewModel.getInvoiceId());
        invoice.setCustomerId(invoiceViewModel.getCustomerId());
        invoice.setPurchaseDate(invoiceViewModel.getPurchaseDate());
        invoiceDao.updateInvoice(invoice);

        List<InvoiceItem> invoiceItems = invoiceItemDao.getInvoiceItemsByInvoiceId(invoice.getInvoiceId());
        invoiceItems.stream().forEach(invoiceItem -> {
            invoiceItemDao.deleteInvoiceItem(invoiceItem.getId());
        });

        invoiceItems = invoiceViewModel.getInvoiceItems();
        invoiceItems.stream().forEach(invoiceItem -> {
            invoiceItem.setInvoiceId(invoiceViewModel.getInvoiceId());
            invoiceItemDao.addInvoiceItem(invoiceItem);
        });
    }

    public List<InvoiceViewModel> getAllInvoices() {
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        List<InvoiceViewModel> invoiceViewModels = new ArrayList<>();
        for (Invoice invoice: invoices) {
            invoiceViewModels.add(buildInvoiceViewModel(invoice));
        }
        return invoiceViewModels;
    }

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setPurchaseDate(invoice.getPurchaseDate());
        invoiceViewModel.setCustomerId(invoice.getCustomerId());
        List<InvoiceItem> invoiceItems = invoiceItemDao.getInvoiceItemsByInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setInvoiceItems(invoiceItems);
        return invoiceViewModel;
    }
}
