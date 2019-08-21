package com.company.retailservice.service;

import com.company.retailservice.dto.*;
import com.company.retailservice.feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RetailService {


    // Dependencies

    @Autowired
    private ProductServiceClient productServiceClient;
    @Autowired
    private CustomerServiceClient customerServiceClient;
    @Autowired
    private InventoryServiceClient inventoryServiceClient;
    @Autowired
    private LevelUpServiceClient levelUpServiceClient;
    @Autowired
    private InvoiceServiceClient invoiceServiceClient;


    // constructor

    // Service Layer public methods:

    public Product getByProductId(int id) {

        return productServiceClient.getProductById(id);
    }

    public OrderResponseView createOrder(OrderRequestView orderRequestView) {

        // check if the products are in stock
        List<InputItem> inStockList = checkIfInStock(orderRequestView.getProducts());

        if ( inStockList.size() == 0 ) {
            // all products ordered are out of stock
            // maybe throw execption "out of stock"
            return null;
        }

        // Create an OrderResponseView to return
        OrderResponseView returnView = new OrderResponseView();

        // Check if the customer exists or needs to be created
            // add customer object to the return view
        returnView.setCustomer(checkCustomer(orderRequestView.getCustomer()));

        // create invoice with invoiceItems
        InvoiceViewModel tempInvoice = createInvoice(returnView.getCustomer().getId(), inStockList);












        return null;

    }


    // Helper Methods

    public List<InvoiceItem> checkIfInStock(List<InputItem> itemList) {
        // loop the list of products to see if they are in stock
        // if they are available put them in a temp list
        List<InvoiceItem> tempProductList = new ArrayList<>();

        for ( InputItem each: itemList) {

            // get Inventory object for each productId
            Inventory tempInventory = inventoryServiceClient.getInventoryByProductId(each.getProductId());
            if ( each.getQuantity() <= tempInventory.getQuantity()) {





//               InvoiceItem tempInvoiceItem = new InvoiceItem();
//               // do we need to include the instock qty and the needed qty
//
//               tempInvoiceItem.setInventoryId(tempInventory.getInventoryId());
//
//               tempInvoiceItem.setQuantity(each.getQuantity());
//
//                tempProductList.add(tempInvoiceItem);
            }

        }
        // now we have a list of products that are in stock.
        return  tempProductList;
    }



    public Customer checkCustomer (Customer customer) {

        if (customer.getId() < 0 ) {
            // create a new Customer
            customer = customerServiceClient.createCustomer(customer);
        }

        return customer;
    }


    public InvoiceViewModel createInvoice(int customerId, List<InvoiceItem> productList ) {

        // create an InvoiceViewModel with the in-stock products
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        // create a temp Invoice object
        Invoice invoice = new Invoice();
        invoice.setCustomerId(customerId);
        invoice.setPurchaseDate(LocalDate.now());

        invoiceViewModel.setInvoice(invoice);
        invoiceViewModel.setInvoiceItems(productList);

        return invoiceServiceClient.createInvoice(invoiceViewModel);
    }


    public BigDecimal totalOrderPrice(InvoiceViewModel invoiceViewModel) {


        BigDecimal cost = new BigDecimal(0.00).setScale(2);

        for ( InvoiceItem each: invoiceViewModel.getInvoiceItems() ) {

            BigDecimal tempQuantity = new BigDecimal(each.getQuantity() );
//            BigDecimal tempPrice = new BigDecimal(each.getUnitPrice() );
            cost += each.getUnitPrice().multiply(tempQuantity);
        }


        return cost;
    }

    // send customerId and return LevelUp object
    // do math and
    // send LevelUp object with updated point to que


    public int includeLevelUp(int customerId, BigDecimal totalPrice) {

        LevelUp tempLevelUp = levelUpServiceClient.getLevelUp(customerId);

        // divide the totalPrice by 50

        int factor = BigDecimal.valueOf(totalPrice.longValue())
                .divide(BigDecimal.valueOf(50)).intValue();


        // use this int to calculate the total points
        int newPoints = factor * 10;

        // add the new points to the existing points
        int newLevelUpTotal = tempLevelUp.getPoints() + newPoints;
        // update the new total in the tempLevelUp object
        tempLevelUp.setPoints(newLevelUpTotal);

        // send the updated object to the que






        // --------------------------------------
        // return the new total
        return newLevelUpTotal;
    }























}
