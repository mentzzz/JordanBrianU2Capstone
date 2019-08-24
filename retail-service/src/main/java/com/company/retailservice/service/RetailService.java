package com.company.retailservice.service;

import com.company.retailservice.dto.*;
import com.company.retailservice.feign.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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

    public RetailService(LevelUpServiceClient client) {
        this.levelUpServiceClient = client;
    }



    @HystrixCommand(fallbackMethod = "getLevelUpPoints")
    public List<LevelUp> levelUpPoints(int id) {

        return levelUpServiceClient.getLevelUpByCustomer(id);
    }
    // constructor

    // Service Layer public methods:

    public List<LevelUp> getLevelUpPoints(int customerId) {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(customerId);
        levelUp.setPoints(0);
        levelUp.setMemberDate(LocalDate.now());
        List<LevelUp> returnList = new ArrayList<>();
        returnList.add(levelUp);

//        return levelUpServiceClient.getLevelUpByCustomer(customerId);
        return returnList;
    }

    public Product getByProductId(int id) {

        return productServiceClient.getProductById(id);
    }

    public List<Product> getAllProducts() {

        return productServiceClient.getAllProducts();
    }

    public OrderViewModel makeOrder(OrderViewModel model) throws Exception {
        OrderViewModel orderViewModel = new OrderViewModel();
//        orderViewModel.setInvoiceId(model.getInvoiceId());
//        orderViewModel.setProductId(model.getProductId());
//        orderViewModel.setProducts(model.getProducts());
//
        Customer customer = checkCustomer(model.getCustomer());

        InvoiceViewModel invoice = invoiceServiceClient.getInvoiceById(model.getInvoiceId());
        List<ProductOrder> products = model.getProducts();
        Inventory inventory = inventoryServiceClient.getInventoryByProductId(model.getProductId());
//        if (inventoryServiceClient.getQuantity(model.getProduct().getId()) > 0 )
        BigDecimal total = invoiceServiceClient.getTotal(model.getInvoiceId());
        Integer totalQuantity = invoiceServiceClient.getQuantity(model.getInvoiceId());
        BigDecimal newTotal = total.multiply(new BigDecimal(totalQuantity));


        List<LevelUp> levelUps = levelUpServiceClient.getLevelUpByCustomer(model.getCustomer().getId());
        Product product = productServiceClient.getProductById(model.getProductId());
        List<InvoiceViewModel> item = invoiceServiceClient.getItemByInvoice(model.getProductId());
        item.stream()
                .forEach(n -> {
                    n.setInvoice(invoiceServiceClient.getInvoiceById(model.getInvoiceId()));
                    invoiceServiceClient.createInvoice(n);
                );

                        });
//        product.getListPrice() *
        orderViewModel.setInvoiceId(invoice.getInvoice().getInvoiceId());
//        model.setCustomer(customer);
        orderViewModel.setProducts(model.getProducts());
//        model.setProducts(products);
        orderViewModel.setCustomer(model.getCustomer());
        orderViewModel.setTotal(newTotal);
        orderViewModel.se
        //setting total points by calling sum method to add all total points in database
        model.setTotalPoints(levelUpServiceClient.getTotalPoints(model.getCustomer().getId()));
        if (model.getQuantity() > 0 && model.getQuantity() <= inventoryServiceClient.getQuantity(model.getProduct().getId()))
            return model;
        else
            throw new Exception("Quantity must be greater than zero or your product isn't in stock ");

    }


    public OrderResponseView createOrder(OrderRequestView orderRequestView) {

        System.out.println("In the serviceLayer");
        System.out.println(orderRequestView.toString());

        // ******* may need to add the product listPrice in the request view  *********

        // check if the products are in stock
        List<InStockProducts> inStockProducts = checkIfInStock(orderRequestView.getProducts());

        if (inStockProducts.size() == 0) {
            // all products ordered are out of stock
            // maybe throw execption "out of stock"
            return null;
        }


        // Check if the customer exists or needs to be created
        Customer customer = checkCustomer(orderRequestView.getCustomer());
        System.out.println(customer.toString());


        // create a ProductInvoice object for each item ( new )
        // add the product object
        List<ProductInvoice> productInvoices = addProductInfo(inStockProducts,customer.getId());

        // create and add Invoice and Invoice Item to each ProductInvoice object
        productInvoices = createInvoice(productInvoices);
        System.out.println(productInvoices.toString());


        // need to calculate the total price for each product
        productInvoices = totalPriceForEachProduct(productInvoices);
        System.out.println(productInvoices.toString());

        // Now create a singleInvoice object to begin populating
        SingleInvoice singleInvoice = createSingleInvoice(productInvoices);
        System.out.println(singleInvoice.toString());

        // create LevelUpInfo object
        LevelUpInfo levelUpInfo = createLevelUp(customer.getId(), singleInvoice.getOrderTotalPrice() );
        System.out.println(levelUpInfo.toString());

        // create OrderResponseView object

        return createResponseView(singleInvoice, customer, levelUpInfo );

    }


    // Helper Methods

    public OrderResponseView createResponseView(SingleInvoice singleInvoice, Customer customer, LevelUpInfo levelUpInfo) {
        // Create an OrderResponseView to return
        OrderResponseView returnView = new OrderResponseView();
        returnView.setCustomer(customer);
        returnView.setOrder(singleInvoice);
        returnView.setLevelUpInfo(levelUpInfo);

        return returnView;
    }





    public LevelUpInfo createLevelUp(int customerId, BigDecimal totalPrice) {

        // ***** need to add logic here from the planning file



//        LevelUp tempLevelUp = levelUpServiceClient.getLevelUpByCustomer(customerId);
//
//        // divide the totalPrice by 50
//
//        int factor = BigDecimal.valueOf(totalPrice.longValue())
//                .divide(BigDecimal.valueOf(50)).intValue();
//
//
//        // use this int to calculate the total points
//        int newPoints = factor * 10;
//
//        // add the new points to the existing points
//        int newLevelUpTotal = tempLevelUp.getPoints() + newPoints;
//        // update the new total in the tempLevelUp object
//        tempLevelUp.setPoints(newLevelUpTotal);
//
//        // send the updated object to the que
//
//
//        //if level up has ID then its update, no level up id is create
//
//
//        // --------------------------------------
//        // return the new total

        LevelUpInfo levelUpInfo = new LevelUpInfo();



        return levelUpInfo;
    }




    public SingleInvoice createSingleInvoice(List<ProductInvoice> productInvoices) {

        // get the first index of the list to get common data to populate
        ProductInvoice commonData = productInvoices.get(0);

        // loop the productInvoices list to create OrderProducts objects
        // List to hold all the products for this invoice
        List<OrderProducts> productList = new ArrayList<>();


        for ( ProductInvoice each: productInvoices ) {
            OrderProducts orderProducts = new OrderProducts();
            orderProducts.setInvoiceItemId(each.getInvoiceItem().getInvoiceItemId());
            orderProducts.setQuantity(each.getQuantity());
            orderProducts.setListPrice(each.getProduct().getListPrice());
            orderProducts.setTotalPrice(each.getTotalPrice());
            orderProducts.setProductName(each.getProduct().getProductName());
            orderProducts.setProductDescription(each.getProduct().getProductDescription());

            productList.add(orderProducts);
        }

        // start to populate the singleInvoice object
        SingleInvoice singleInvoice = new SingleInvoice();
        singleInvoice.setInvoiceId(commonData.getInvoiceId());
        // call method of calcualte order total
        singleInvoice.setOrderTotalPrice(orderTotalPrice(productList));
        singleInvoice.setOrderItems(productList);

        // so, singleInvoice object is created

        return singleInvoice;
    }

    public BigDecimal orderTotalPrice(List<OrderProducts> productList) {
        BigDecimal orderTotal = new BigDecimal(0.00).setScale(2);

        for ( OrderProducts each: productList ) {
            orderTotal = orderTotal.add(each.getTotalPrice());
        }

        return orderTotal;
    }


    public List<ProductInvoice> totalPriceForEachProduct(List<ProductInvoice> productInvoices) {

//        BigDecimal orderTotal = new BigDecimal(0.00).setScale(2);

        // need to find where the quantity and list price are in the object
        for ( ProductInvoice each: productInvoices ) {
            BigDecimal productTotal = new BigDecimal(0.00).setScale(2);
            BigDecimal tempQuantity = new BigDecimal(each.getQuantity() );
            BigDecimal productPrice = each.getProduct().getListPrice();

            productTotal = tempQuantity.multiply(productPrice);

            each.setTotalPrice(productTotal);
        }

        return productInvoices;
    }



    public List<ProductInvoice> addProductInfo(List<InStockProducts> inStockProduct, int customerId) {
        List<ProductInvoice> productInvoiceList = new ArrayList<>();

        for (InStockProducts each : inStockProduct) {
            ProductInvoice productInvoice = new ProductInvoice();
            productInvoice.setCustomerId(customerId);
            // call service for product info
            productInvoice.setProduct(productServiceClient.getProductById(each.getProductId()));
            productInvoice.setInventory(each.getInventory());
            productInvoice.setQuantity(each.getQuantity());

            // add this object to the return list
            productInvoiceList.add(productInvoice);

        }
        return productInvoiceList;
    }

    public List<ProductInvoice> createInvoice(List<ProductInvoice> inStockProduct) {

        // List of InvoiceItems to populate
        List<InvoiceItem> invoiceItemsList = new ArrayList<>();
        // loop the list and send a create invoice for each
        for (ProductInvoice each : inStockProduct) {
            //temp InvoiceItem to populate
            InvoiceItem invoiceItem = new InvoiceItem();

            invoiceItem.setInventoryId(each.getInventory().getInventoryId());
            invoiceItem.setQuantity(each.getQuantity());
            invoiceItem.setUnitPrice(each.getProduct().getListPrice());

            invoiceItemsList.add(invoiceItem);
        }

        // temp InvoiceViewModel to populate
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        // temp Invoice to populate
        Invoice invoice = new Invoice();
        invoice.setCustomerId(inStockProduct.get(0).getCustomerId());
        invoice.setPurchaseDate(LocalDate.now());


        invoiceViewModel.setInvoiceItems(invoiceItemsList);
        invoiceViewModel.setInvoice(invoice);

        InvoiceViewModel fromService = invoiceServiceClient.createInvoice(invoiceViewModel);

        // add the data from the invoice to the productInvoice object

        for ( ProductInvoice eachProductInvoice : inStockProduct ) {

            for ( InvoiceItem eachInvoiceItem : fromService.getInvoiceItems() ) {

                if( eachProductInvoice.getInventory().getInventoryId() == eachInvoiceItem.getInventoryId() ) {
                    // inventoryId's match so set the invoiceItem data
                    eachProductInvoice.setInvoiceItem(eachInvoiceItem);
                    eachProductInvoice.setInvoiceId(eachInvoiceItem.getInvoiceId());
                }
            }

        }

        return inStockProduct;
    }


    public List<InStockProducts> checkIfInStock(List<InputItem> itemList) {
        // loop the list of products to see if they are in stock
        // if they are available put them in a temp list

        List<InStockProducts> inStockProducts = new ArrayList<>();

        // Create a List<Inventory> to popoulate with the updated qty of instock products
        List<Inventory> updatedInventoryList = new ArrayList<>();


        for (InputItem each : itemList) {

            // get Inventory object for each productId
            Inventory tempInventory = inventoryServiceClient.getInventoryByProductId(each.getProductId());
            if (each.getQuantity() <= tempInventory.getQuantity()) {

                InStockProducts tempInStock = new InStockProducts();
                tempInStock.setProductId(each.getProductId());
                tempInStock.setQuantity(each.getQuantity());
                tempInStock.setInventory(tempInventory);

                inStockProducts.add(tempInStock);

                // Also, create a temp Inventory object to update the qty
                Inventory updatedInventory = new Inventory();
                updatedInventory.setInventoryId(tempInventory.getInventoryId());
                updatedInventory.setProductId(each.getProductId());
                updatedInventory.setQuantity(tempInventory.getQuantity() - each.getQuantity());
                // add this temp Inventory to the updatedInventoryList
                updatedInventoryList.add(updatedInventory);
            }

        }

        // Send a List back to Inventory service to update the stock qty's
        if (updatedInventoryList.size() > 0) {
            updateInventory(updatedInventoryList);
        }

        return inStockProducts;
    }


    public void updateInventory(List<Inventory> list) {
        for (Inventory each : list) {
            inventoryServiceClient.updateInventory(each.getInventoryId(), each);
        }
    }




    public Customer checkCustomer(Customer customer) {

        if (customer.getId() <= 0) {
            // create a new Customer
            customer = customerServiceClient.createCustomer(customer);
        }

        return customer;
    }





































































//    public OrderResponseView createOrder(OrderRequestView orderRequestView) {
//
//        // ******* may need to add the product listPrice in the request view  *********
//
//        // check if the products are in stock
//        InvoiceViewModel tempInvoiceViewModel = checkIfInStock(orderRequestView.getProducts());
//
//        if ( tempInvoiceViewModel.getInvoiceItems().size() == 0 ) {
//            // all products ordered are out of stock
//            // maybe throw execption "out of stock"
//            return null;
//        }
//
//        // Create an OrderResponseView to return
//        OrderResponseView returnView = new OrderResponseView();
//        // Create a SingleInvoice to populate and add to the returnView object
//        SingleInvoice singleInvoice = new SingleInvoice();
//
//
//        // Check if the customer exists or needs to be created
//            // add customer object to the return view
//        returnView.setCustomer(checkCustomer(orderRequestView.getCustomer()));
//
//        // populate the tempInvoiceViewModel with customer info
//            // create a temp Inoice object to help
//            Invoice tempInvoice = new Invoice();
//            tempInvoice.setCustomerId(returnView.getCustomer().getId());
//            tempInvoice.setPurchaseDate(LocalDate.now());
//
//        tempInvoiceViewModel.setInvoice(tempInvoice);
//
//
//        // create invoice with invoiceItems by passing in the tempInvoiceViewModel
//            // use the previously defined tempInvoiceViewModel to capture the return
//
//        tempInvoiceViewModel = createInvoice(tempInvoiceViewModel);
//
//     // what does tempInvoiceViewModel look like?
//
//        // need a dto that included product details
//
//        // what is in singleinvoice lsit?  List of TempInvoice items which includes product
//
//
//        singleInvoice.setOrderItems(addProductInfo(tempInvoiceViewModel)); // call the add products() here
//
//
//
//        // calculate the total price
//            // add total price to singleInvoice object
//        singleInvoice.setOrderTotalPrice(totalOrderPrice(singleInvoice.getOrderItems()));
////        singleInvoice.setOrderTotalPrice(totalOrderPrice(tempInvoiceViewModel));
//
//        // add level up points to the singleInvoice object
//        singleInvoice.setLevelUpPoints(includeLevelUp(tempInvoice.getCustomerId(), singleInvoice.getOrderTotalPrice()));
//
//        // populate the response object with singleInvoice
//        returnView.setOrder(singleInvoice);
//
//
//
//        return returnView;
//
//    }
//
//
//
//
//
//    // Helper Methods
//
//    public InvoiceViewModel checkIfInStock(List<InputItem> itemList) {
//        // loop the list of products to see if they are in stock
//        // if they are available put them in a temp list
//
//        List<InvoiceItem> tempInvoiceViewList = new ArrayList<>();
//
//        for ( InputItem each: itemList) {
//
//            // get Inventory object for each productId
//            Inventory tempInventory = inventoryServiceClient.getInventoryByProductId(each.getProductId());
//            if ( each.getQuantity() <= tempInventory.getQuantity()) {
//
//               InvoiceItem tempInvoiceItem = new InvoiceItem();
//               tempInvoiceItem.setInventoryId(tempInventory.getInventoryId());
//               tempInvoiceItem.setQuantity(each.getQuantity());
//               // need to add price here
//
//               tempInvoiceViewList.add(tempInvoiceItem);
//
//            }
//
//        }
//        // now we have a list of products that are in stock.
//
//        // create a InvoiceViewModel object
//        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
//        invoiceViewModel.setInvoiceItems(tempInvoiceViewList);
//
//
//        return  invoiceViewModel;
//    }
//
//
//    public void updateQuantity(int inventoryId, Inventory inventory) {
//
//
//    }
//
//
//
//    public Customer checkCustomer (Customer customer) {
//
//        if (customer.getId() < 0 ) {
//            // create a new Customer
//            customer = customerServiceClient.createCustomer(customer);
//        }
//
//        return customer;
//    }
//
//
//    public InvoiceViewModel createInvoice(InvoiceViewModel invoiceViewModel ) {
//        // returns a InvoiceViewModel with invoiceId and invoiceItemId's
//        return invoiceServiceClient.createInvoice(invoiceViewModel);
//    }
//
//
//    public BigDecimal totalOrderPrice(List<TempInvoiceItem> itemList) {
//
//        BigDecimal total = new BigDecimal(0.00).setScale(2);
//
//        for ( TempInvoiceItem each: itemList ) {
//            total = total.add(each.getProductTotalPrice());
//
//        }
//
//        return  total;
//    }
//
//
//
//    public int includeLevelUp(int customerId, BigDecimal totalPrice) {
//
//        List<LevelUp> tempLevelUp = levelUpServiceClient.getLevelUpByCustomer(customerId);
//
//        // divide the totalPrice by 50
//
//        int factor = BigDecimal.valueOf(totalPrice.longValue())
//                .divide(BigDecimal.valueOf(50)).intValue();
//
//
//        // use this int to calculate the total points
//        int newPoints = factor * 10;
//
//        // add the new points to the existing points
//        int newLevelUpTotal = tempLevelUp.getPoints() + newPoints;
//        // update the new total in the tempLevelUp object
//        tempLevelUp.setPoints(newLevelUpTotal);
//
//        // send the updated object to the que
//
//
//        //if level up has ID then its update, no level up id is create
//
//
//
//
//        // --------------------------------------
//        // return the new total
//        return newLevelUpTotal;
//    }
//
//
//    public List<TempInvoiceItem> addProductInfo(InvoiceViewModel invoiceViewModel) {
//        // TempInvoiceItem list to return
//        List<TempInvoiceItem> returnList = new ArrayList<>();
//
//        for ( InvoiceItem each: invoiceViewModel.getInvoiceItems() ) {
//            TempInvoiceItem tempInvoiceItem = new TempInvoiceItem();
//
//            tempInvoiceItem.setInvoiceId(invoiceViewModel.getInvoice().getInvoiceId());
//            tempInvoiceItem.setInvoiceItemId(each.getInvoiceItemId());
//
//            // call the inventory service and pass in the inventoryId to find product Id
//                // invoiceItemId = each.getInvoiceItemdId()
//            Inventory inventory = inventoryServiceClient.getInventoryById(each.getInventoryId());
//
//            // call the product service with the productId from above
//            Product product = productServiceClient.getProductById(inventory.getProductId());
//
//            // add the Product Object to the tempinvoiceItem
//            tempInvoiceItem.setProduct(product);
//
//            // add quantity ordered
//            tempInvoiceItem.setQuantity(each.getQuantity());
//
//            // calculate the total for this product
//            BigDecimal tempQuantity = new BigDecimal(each.getQuantity() );
//            BigDecimal invoiceItemTotal = each.getUnitPrice().multiply(tempQuantity);
//            tempInvoiceItem.setProductTotalPrice(invoiceItemTotal);
//
//
//            // add tempinvoiceItem to the returnList
//            returnList.add(tempInvoiceItem);
//
//
//        }
//
//        return returnList;
//    }




















}
