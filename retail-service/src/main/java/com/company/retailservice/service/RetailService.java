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

    public List<Product> getAllProducts() {

        return productServiceClient.getAllProducts();
    }


    public OrderResponseView createOrder(OrderRequestView orderRequestView) {

        // ******* may need to add the product listPrice in the request view  *********

        // check if the products are in stock
        InvoiceViewModel tempInvoiceViewModel = checkIfInStock(orderRequestView.getProducts());

        if ( tempInvoiceViewModel.getInvoiceItems().size() == 0 ) {
            // all products ordered are out of stock
            // maybe throw execption "out of stock"
            return null;
        }

        // Create an OrderResponseView to return
        OrderResponseView returnView = new OrderResponseView();
        // Create a SingleInvoice to populate and add to the returnView object
        SingleInvoice singleInvoice = new SingleInvoice();


        // Check if the customer exists or needs to be created
            // add customer object to the return view
        returnView.setCustomer(checkCustomer(orderRequestView.getCustomer()));

        // populate the tempInvoiceViewModel with customer info
            // create a temp Inoice object to help
            Invoice tempInvoice = new Invoice();
            tempInvoice.setCustomerId(returnView.getCustomer().getId());
            tempInvoice.setPurchaseDate(LocalDate.now());

        tempInvoiceViewModel.setInvoice(tempInvoice);


        // create invoice with invoiceItems by passing in the tempInvoiceViewModel
            // use the previously defined tempInvoiceViewModel to capture the return

        tempInvoiceViewModel = createInvoice(tempInvoiceViewModel);

     // what does tempInvoiceViewModel look like?

        // need a dto that included product details

        // what is in singleinvoice lsit?  List of TempInvoice items which includes product


        singleInvoice.setOrderItems(addProductInfo(tempInvoiceViewModel)); // call the add products() here



        // calculate the total price
            // add total price to singleInvoice object
        singleInvoice.setOrderTotalPrice(totalOrderPrice(singleInvoice.getOrderItems()));
//        singleInvoice.setOrderTotalPrice(totalOrderPrice(tempInvoiceViewModel));

        // add level up points to the singleInvoice object
        singleInvoice.setLevelUpPoints(includeLevelUp(tempInvoice.getCustomerId(), singleInvoice.getOrderTotalPrice()));

        // populate the response object with singleInvoice
        returnView.setOrder(singleInvoice);



        return returnView;

    }





    // Helper Methods

    public InvoiceViewModel checkIfInStock(List<InputItem> itemList) {
        // loop the list of products to see if they are in stock
        // if they are available put them in a temp list

        List<InvoiceItem> tempInvoiceViewList = new ArrayList<>();

        for ( InputItem each: itemList) {

            // get Inventory object for each productId
            Inventory tempInventory = inventoryServiceClient.getInventoryByProductId(each.getProductId());
            if ( each.getQuantity() <= tempInventory.getQuantity()) {

               InvoiceItem tempInvoiceItem = new InvoiceItem();
               tempInvoiceItem.setInventoryId(tempInventory.getInventoryId());
               tempInvoiceItem.setQuantity(each.getQuantity());
               // need to add price here

               tempInvoiceViewList.add(tempInvoiceItem);

            }

        }
        // now we have a list of products that are in stock.

        // create a InvoiceViewModel object
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setInvoiceItems(tempInvoiceViewList);


        return  invoiceViewModel;
    }


    public void updateQuantity(int inventoryId, Inventory inventory) {


    }



    public Customer checkCustomer (Customer customer) {

        if (customer.getId() < 0 ) {
            // create a new Customer
            customer = customerServiceClient.createCustomer(customer);
        }

        return customer;
    }


    public InvoiceViewModel createInvoice(InvoiceViewModel invoiceViewModel ) {
        // returns a InvoiceViewModel with invoiceId and invoiceItemId's
        return invoiceServiceClient.createInvoice(invoiceViewModel);
    }


    public BigDecimal totalOrderPrice(List<TempInvoiceItem> itemList) {

        BigDecimal total = new BigDecimal(0.00).setScale(2);

        for ( TempInvoiceItem each: itemList ) {
            total = total.add(each.getProductTotalPrice());

        }

        return  total;
    }



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


        //if level up has ID then its update, no level up id is create




        // --------------------------------------
        // return the new total
        return newLevelUpTotal;
    }


    public List<TempInvoiceItem> addProductInfo(InvoiceViewModel invoiceViewModel) {
        // TempInvoiceItem list to return
        List<TempInvoiceItem> returnList = new ArrayList<>();

        for ( InvoiceItem each: invoiceViewModel.getInvoiceItems() ) {
            TempInvoiceItem tempInvoiceItem = new TempInvoiceItem();

            tempInvoiceItem.setInvoiceId(invoiceViewModel.getInvoice().getInvoiceId());
            tempInvoiceItem.setInvoiceItemId(each.getInvoiceItemId());

            // call the inventory service and pass in the inventoryId to find product Id
                // invoiceItemId = each.getInvoiceItemdId()
            Inventory inventory = inventoryServiceClient.getInventoryById(each.getInventoryId());

            // call the product service with the productId from above
            Product product = productServiceClient.getProductById(inventory.getProductId());

            // add the Product Object to the tempinvoiceItem
            tempInvoiceItem.setProduct(product);

            // add quantity ordered
            tempInvoiceItem.setQuantity(each.getQuantity());

            // calculate the total for this product
            BigDecimal tempQuantity = new BigDecimal(each.getQuantity() );
            BigDecimal invoiceItemTotal = each.getUnitPrice().multiply(tempQuantity);
            tempInvoiceItem.setProductTotalPrice(invoiceItemTotal);


            // add tempinvoiceItem to the returnList
            returnList.add(tempInvoiceItem);


        }

        return returnList;
    }




















}
