package com.company.invoiceservice.service;

import com.company.invoiceservice.dao.InvoiceDao;
import com.company.invoiceservice.dao.InvoiceDaoJdbcTemplateImpl;
import com.company.invoiceservice.dao.InvoiceItemDao;
import com.company.invoiceservice.dao.InvoiceItemDaoJdbcTemplateImpl;
import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.viewmodel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class InvoiceServiceTest {
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;

    InvoiceService service;

    @Before
    public void setUp() throws Exception {
        setUpInvoiceDaoMock();
        setUpInvoiceItemDaoMock();

        service = new InvoiceService(invoiceDao, invoiceItemDao);

    }

    private void setUpInvoiceDaoMock() {
        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2022, 12, 12));

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(1);
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2022, 12, 12));

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);

        doReturn(invoice1).when(invoiceDao).addInvoice(invoice);
        doReturn(invoices).when(invoiceDao).getAllInvoices();
        doReturn(invoice1).when(invoiceDao).getInvoiceById(1);
    }

    private void setUpInvoiceItemDaoMock() {
        invoiceItemDao = mock(InvoiceItemDaoJdbcTemplateImpl.class);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitPrice(new BigDecimal(200.00).setScale(2));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(1);
        invoiceItem1.setQuantity(1);
        invoiceItem1.setUnitPrice(new BigDecimal(200.00).setScale(2));

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        invoiceItems.add(invoiceItem1);

        doReturn(invoiceItem1).when(invoiceItemDao).addInvoiceItem(invoiceItem);
        doReturn(invoiceItem1).when(invoiceItemDao).getInvoiceItemById(1);
        doReturn(invoiceItems).when(invoiceItemDao).getAllInvoiceItems();
    }

    @Test
    public void saveFindFindAllInvoice() {
        InvoiceViewModel toService = new InvoiceViewModel();

        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2022, 12, 12));

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitPrice(new BigDecimal(200.00).setScale(2));

        List<InvoiceItem> itemList = new ArrayList<>();
        itemList.add(invoiceItem);

        toService.setInvoice(invoice);
        toService.setInvoiceItems(itemList);

        InvoiceViewModel fromService = new InvoiceViewModel();

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(1);
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2022, 12, 12));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(1);
        invoiceItem1.setQuantity(1);
        invoiceItem1.setUnitPrice(new BigDecimal(200.00).setScale(2));

        List<InvoiceItem> itemList1 = new ArrayList<>();
        itemList1.add(invoiceItem1);

        fromService.setInvoice(invoice1);
        fromService.setInvoiceItems(itemList1);

        List<InvoiceViewModel> listOfInventories = new ArrayList<>();
        listOfInventories.add(fromService);



        toService = service.saveInvoice(toService);
        InvoiceViewModel answer = service.findInvoice(toService.getInvoice().getInvoiceId());

        assertEquals(toService, answer);

        List<InvoiceViewModel> list = service.getAllInvoices();
        assertEquals(1, list.size());

    }


}