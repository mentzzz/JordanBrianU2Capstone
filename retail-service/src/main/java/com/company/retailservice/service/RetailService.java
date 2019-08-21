package com.company.retailservice.service;

import com.company.retailservice.dto.*;
import com.company.retailservice.feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public SearchResponseView getAllProducts() {

        SearchResponseView returnList = new SearchResponseView();

        returnList.setProductList(productServiceClient.getAllProducts());

        return returnList;
    }

    public OrderResponseView createOrder(OrderRequestView orderRequestView) {


        // loop the list of products to see if they are in stock
        // if they are available put them in a temp list
        List<InputItem> tempProductList = new ArrayList<>();
        for ( InputItem each: orderRequestView.getProducts()) {
            int inventoryQuantity = inventoryServiceClient.getByProductId(each.getProductId());
            if ( inventoryQuantity >= each.getQuantity() ) {
               tempProductList.add(each);
            }
        }

        // now we have a list of products that are in stock.



        // Create an OrderResponseView to return
        OrderResponseView returnView = new OrderResponseView();

        // check to see if the Customer object exists
        if (orderRequestView.getCustomer() == null ) {
            // if not create a new Customer

            returnView.setCustomer(customerServiceClient.);
        } else {
            returnView.setCustomer(orderRequestView.getCustomer());
        }

        // Now create a new invoice from invoice - service

        Invoice tempInvoice = invoiceServiceClient.createInvoice(returnView.getCustomer().getCustomerId());

        // create a SingleInvoice object to put the tii created below into
        SingleInvoice singleInvoice = new SingleInvoice();

        // Now loop thru the list of products and begin to populate a tempInvoiceItem object

        for ( InputItem each: tempProductList ) {

            // create a TempInvoiceItem to hold the data for each product purchased
            TempInvoiceItem tii = new TempInvoiceItem();

            // get the product by id
            Product product = productServiceClient.getProductById(each.getProductId());

            tii.setProduct(product);
            tii.setQuantity(each.getQuantity());
            tii.setInvoiceId(tempInvoice.getInvoiceId());

            // send to invoice-item-service
            tii.setInvoiceItemId(invoiceServiceClient.createInvoiceItem(tempInvoice.getInvoiceId(),
                    each.getQuantity(), product.getListPrice()));

            tii.setProductTotalPrice();  // this will automatically do the math for total price

            // add this tii to the list of the SingleInvoice object
            singleInvoice.setOrderItems(tii);


        }














        return null;

    }

}
