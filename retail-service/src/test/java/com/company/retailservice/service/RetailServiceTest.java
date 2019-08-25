package com.company.retailservice.service;

import com.company.retailservice.dto.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RetailServiceTest {

    RetailService service;

    @Before
    public void setUp() throws Exception {

        setUpRetailServiceMock();
    }

    public void setUpRetailServiceMock() {

        service = mock(RetailService.class);

        Product product = new Product();
        product.setId(1);
        product.setProductName("game");
        product.setProductDescription("video");
        product.setListPrice(new BigDecimal(200).setScale(2));
        product.setUnitCost(new BigDecimal(100).setScale(2));

        List<Product> productList = new ArrayList<>();
        productList.add(product);


        // make a requestView object
        OrderRequestView orv = new OrderRequestView();

        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("brian");
        customer.setLastName("smith");
        customer.setStreet("long");
        customer.setCity("Char");
        customer.setZip("28262");
        customer.setEmail("email.com");
        customer.setPhone("336-755-5555");

        InputItem inputItem = new InputItem();
        inputItem.setProductId(1);
        inputItem.setQuantity(1);

        List<InputItem> itemList = new ArrayList<>();
        itemList.add(inputItem);

        orv.setCustomer(customer);
        orv.setProducts(itemList);


        // make a responseView object
        OrderResponseView responseView = new OrderResponseView();
        responseView.setCustomer(customer);

        // creae LevelUpInfo object
        LevelUpInfo levelUpInfo = new LevelUpInfo();
        levelUpInfo.setCurrentOrderPoints(10);
        levelUpInfo.setTotalPoints(20);

        responseView.setLevelUpInfo(levelUpInfo);

        // create SingleInvoice object
        SingleInvoice singleInvoice = new SingleInvoice();

            // OrderProducts object
        OrderProducts orderProducts = new OrderProducts();
        orderProducts.setInvoiceItemId(1);
        orderProducts.setQuantity(1);
        orderProducts.setListPrice(new BigDecimal(200).setScale(2));
        orderProducts.setTotalPrice(new BigDecimal(200).setScale(2));
        orderProducts.setProductName("game");
        orderProducts.setProductDescription("video");

        List<OrderProducts> orderProductList = new ArrayList<>();
        orderProductList.add(orderProducts);

        // populate singleinvocie
        singleInvoice.setInvoiceId(1);
        singleInvoice.setOrderTotalPrice(new BigDecimal(200).setScale(2));
        singleInvoice.setOrderItems(orderProductList);

        responseView.setOrder(singleInvoice);


        // mock helper methods

        // LevelUp list
        LevelUp levelUp = new LevelUp();
        levelUp.setId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(20);
        levelUp.setMemberDate(LocalDate.of(2009, 12, 12));

        List<LevelUp> levelUpList = new ArrayList<>();
        levelUpList.add(levelUp);


        // Invoice Item
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitPrice(new BigDecimal(200.00).setScale(2));

        // Inventory object
        Inventory inventory = new Inventory();
        inventory.setId(1);
        inventory.setProductId(1);
        inventory.setQuantity(1);


        // list of ProductInvoice objects
        ProductInvoice productInvoice = new ProductInvoice();
        productInvoice.setCustomerId(1);
        productInvoice.setInvoiceId(1);
        productInvoice.setQuantity(1);
        productInvoice.setTotalPrice(new BigDecimal(200).setScale(2));
        productInvoice.setProduct(product);
        productInvoice.setInvoiceItem(invoiceItem);
        productInvoice.setInventory(inventory);

        List<ProductInvoice> productInvoiceList = new ArrayList<>();
        productInvoiceList.add(productInvoice);


        // InStockProducts
        InStockProducts inStockProducts = new InStockProducts();
        inStockProducts.setProductId(1);
        inStockProducts.setQuantity(1);
        inStockProducts.setInventory(inventory);

        List<InStockProducts> inStockProductsList = new ArrayList<>();
        inStockProductsList.add(inStockProducts);


        // list of input items
        List<InputItem> inputItemList = new ArrayList<>();
        inputItemList.add(inputItem);

        // Inventory object list
        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory);



        doReturn(product).when(service).getByProductId(1);
        doReturn(productList).when(service).getAllProducts();
        doReturn(responseView).when(service).createOrder(orv);
        doReturn(responseView).when(service).createResponseView(singleInvoice, customer, levelUpInfo);
        doReturn(levelUpList).when(service).levelUpPoints(1);
        doReturn(singleInvoice).when(service).createSingleInvoice(productInvoiceList);
        doReturn(new BigDecimal(200).setScale(2)).when(service).orderTotalPrice(orderProductList);
        doReturn(productInvoiceList).when(service).totalPriceForEachProduct(productInvoiceList);
        doReturn(productInvoiceList).when(service).addProductInfo(inStockProductsList, 1);
        doReturn(productInvoiceList).when(service).createInvoice(productInvoiceList); // may need to add here
        doReturn(inStockProductsList).when(service).checkIfInStock(inputItemList);
        doNothing().when(service).updateInventory(inventoryList);
        doReturn(customer).when(service).checkCustomer(customer);





    }

    @Test
    public void getProduct() {

        Product product = new Product();
        product.setId(1);
        product.setProductName("game");
        product.setProductDescription("video");
        product.setListPrice(new BigDecimal(200).setScale(2));
        product.setUnitCost(new BigDecimal(100).setScale(2));

        Product fromService = service.getByProductId(1);
        assertEquals(product, fromService);

        Product fromService2 = service.getByProductId(0);
        assertNull(fromService2);
    }

    @Test
    public void getAllProducts() {

        List<Product> fromService = service.getAllProducts();

        assertEquals(1, fromService.size());


    }

    @Test
    public void createOrder() {

        // make a requestView object
        OrderRequestView orv = new OrderRequestView();

        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("brian");
        customer.setLastName("smith");
        customer.setStreet("long");
        customer.setCity("Char");
        customer.setZip("28262");
        customer.setEmail("email.com");
        customer.setPhone("336-755-5555");

        InputItem inputItem = new InputItem();
        inputItem.setProductId(1);
        inputItem.setQuantity(1);

        List<InputItem> itemList = new ArrayList<>();
        itemList.add(inputItem);

        orv.setCustomer(customer);
        orv.setProducts(itemList);

        // make a responseView object
        OrderResponseView responseView = new OrderResponseView();
        responseView.setCustomer(customer);

        // creae LevelUpInfo object
        LevelUpInfo levelUpInfo = new LevelUpInfo();
        levelUpInfo.setCurrentOrderPoints(10);
        levelUpInfo.setTotalPoints(20);

        responseView.setLevelUpInfo(levelUpInfo);

        // create SingleInvoice object
        SingleInvoice singleInvoice = new SingleInvoice();

        // OrderProducts object
        OrderProducts orderProducts = new OrderProducts();
        orderProducts.setInvoiceItemId(1);
        orderProducts.setQuantity(1);
        orderProducts.setListPrice(new BigDecimal(200).setScale(2));
        orderProducts.setTotalPrice(new BigDecimal(200).setScale(2));
        orderProducts.setProductName("game");
        orderProducts.setProductDescription("video");

        List<OrderProducts> orderProductList = new ArrayList<>();
        orderProductList.add(orderProducts);

        // populate singleinvocie
        singleInvoice.setInvoiceId(1);
        singleInvoice.setOrderTotalPrice(new BigDecimal(200).setScale(2));
        singleInvoice.setOrderItems(orderProductList);

        responseView.setOrder(singleInvoice);


        OrderResponseView fromService = service.createOrder(orv);

        assertEquals(responseView, fromService);


    }


}