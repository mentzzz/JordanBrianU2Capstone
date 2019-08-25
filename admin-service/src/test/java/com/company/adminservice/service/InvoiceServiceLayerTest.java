package com.company.adminservice.service;

import com.company.adminservice.dto.Invoice;
import com.company.adminservice.dto.InvoiceItem;
import com.company.adminservice.dto.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class InvoiceServiceLayerTest {

    InvoiceServiceLayer service;

    @Before
    public void setUp() throws Exception {
        setUpInvoiceServiceMock();
    }

    public void setUpInvoiceServiceMock() {

        service = mock(InvoiceServiceLayer.class);

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



        doReturn(fromService).when(service).createInvoice(toService);
        doReturn(listOfInventories).when(service).findAllInvoices();
        doReturn(fromService).when(service).findInvoice(1);


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

        toService = service.createInvoice(toService);
        InvoiceViewModel fromService = service.findInvoice(1);
        List<InvoiceViewModel> listFromService = service.findAllInvoices();

        assertEquals(toService, fromService);
        assertEquals(listFromService.size(), 1);







    }
}