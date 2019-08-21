package com.company.retailservice.dto;

import java.util.Objects;

public class Inventory {


    private int inventoryId;
    private int productId;
    private int quantity;

    // getters / setters

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    // equals / hash

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return inventoryId == inventory.inventoryId &&
                productId == inventory.productId &&
                quantity == inventory.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, productId, quantity);
    }


    // to string


    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
