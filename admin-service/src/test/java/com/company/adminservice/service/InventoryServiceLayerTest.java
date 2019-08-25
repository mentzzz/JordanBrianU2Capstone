package com.company.adminservice.service;

import com.company.adminservice.dto.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class InventoryServiceLayerTest {

    InventoryServiceLayer service;

    @Before
    public void setUp() throws Exception {

        setUpInventoryServiceMock();
    }

    public void setUpInventoryServiceMock() {

        service = mock(InventoryServiceLayer.class);

        Inventory inventory = new Inventory();
        inventory.setId(1);
        inventory.setProductId(1);
        inventory.setQuantity(1);

        Inventory inventory1 = new Inventory();
        inventory1.setProductId(1);
        inventory1.setQuantity(1);

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory);

        doReturn(inventory).when(service).createInventory(inventory1);
        doReturn(inventoryList).when(service).findAllInventorys();
        doReturn(inventory).when(service).findInventory(1);
        doReturn(inventory).when(service).findInventoryByProductId(1);



    }

    @Test
    public void saveFindFindAllInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(1);

        inventory = service.createInventory(inventory);

        Inventory inventory1 = service.findInventory(inventory.getId());
        List<Inventory> inventoryList = service.findAllInventorys();
        assertEquals(inventoryList.size(), 1);
        assertEquals(inventory, inventory1);
    }

    @Test
    public void getInventoryByProduct() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(1);

        inventory = service.createInventory(inventory);

        Inventory inventory2 = service.findInventoryByProductId(inventory.getProductId());

        assertEquals(inventory2, inventory);
    }
}