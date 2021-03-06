package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.viewmodel.InvoiceViewModel;
import org.bouncycastle.pqc.crypto.xmss.BDS;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceItemDao {
    InvoiceItem addInvoiceItem(InvoiceItem invoiceItem);

    InvoiceItem getInvoiceItemById(int id);

    List<InvoiceItem> getAllInvoiceItems();

    void updateInvoiceItem(InvoiceItem invoiceItem);

    void deleteInvoiceItem(int id);

    List<InvoiceItem> getInvoiceItemsByInvoiceId(int id);

    BigDecimal totalPriceItems(int invoiceId);

    int totalQuantityByInvoice(int invoiceId);


    }
