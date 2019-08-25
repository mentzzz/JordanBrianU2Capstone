package com.company.adminservice.service;

import com.company.adminservice.dto.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ProductServiceLayerTest {

    ProductServiceLayer service;

    @Before
    public void setUp() throws Exception {

        setUpProductServiceMock();


    }


    public void setUpProductServiceMock() {

        service = mock(ProductServiceLayer.class);

        Product product = new Product();
        product.setId(1);
        product.setProductName("J");
        product.setProductDescription("M");
        product.setListPrice(new BigDecimal(200).setScale(2));
        product.setUnitCost(new BigDecimal(100).setScale(2));

        Product product1 = new Product();

        product1.setProductName("J");
        product1.setProductDescription("M");
        product1.setListPrice(new BigDecimal(200).setScale(2));
        product1.setUnitCost(new BigDecimal(100).setScale(2));

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        doReturn(product).when(service).createProduct(product1);
        doReturn(product).when(service).findProduct(1);
        doReturn(productList).when(service).findAllProducts();



    }

    @Test
    public void saveFindFindAllProduct() {
        Product product = new Product();
        product.setProductName("J");
        product.setProductDescription("M");
        product.setListPrice(new BigDecimal(200).setScale(2));
        product.setUnitCost(new BigDecimal(100).setScale(2));

        product = service.createProduct(product);
        Product fromService = service.findProduct(product.getId());
        List<Product> productList = service.findAllProducts();
        assertEquals(productList.size(), 1);
        assertEquals(product, fromService);
    }
}