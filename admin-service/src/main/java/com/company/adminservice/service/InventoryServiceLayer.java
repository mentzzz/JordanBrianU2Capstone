package com.company.adminservice.service;

import com.company.adminservice.dto.Inventory;
import com.company.adminservice.feign.InventoryServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryServiceLayer {

    @Autowired
    private InventoryServiceClient inventoryServiceClient;




    // CREATE INVENTORY
    public Inventory createInventory(Inventory inventory) {
        inventory = inventoryServiceClient.createInventory(inventory);
        return inventory;
    }


    // FIND INVENTORY BY ID
    public Inventory findInventory(int id) {
        Inventory inventory = inventoryServiceClient.getInventoryById(id);
        return inventory;
    }

    // FINDALL INVENTORY
    public List<Inventory> findAllInventorys() {
        List<Inventory> inventory = inventoryServiceClient.getAllInventorys();
        return inventory;
    }

    // UPDATE INVENTORY
    public void updateInventory(int id, Inventory inventory) {
        inventoryServiceClient.updateInventory(id, inventory);
    }

    // DELETE INVENTORY BY ID
    public void deleteInventory(int id) {
        inventoryServiceClient.deleteInventory(id);
    }



    // FIND INVENTORY BY PRODUCT ID
    public Inventory findInventoryByProductId(int id) {
        Inventory inventory = inventoryServiceClient.getInventoryByProductId(id);
        return inventory;
    }






}
