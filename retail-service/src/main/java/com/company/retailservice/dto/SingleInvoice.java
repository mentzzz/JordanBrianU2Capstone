package com.company.retailservice.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class SingleInvoice {

    private int invoiceId;

    private List<TempInvoiceItem> orderItems;

    private BigDecimal orderTotalPrice;
    private int levelUpPoints;


    // getters / setters

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public List<TempInvoiceItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TempInvoiceItem> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public int getLevelUpPoints() {
        return levelUpPoints;
    }

    public void setLevelUpPoints(int levelUpPoints) {
        this.levelUpPoints = levelUpPoints;
    }


    // equals / hash

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleInvoice that = (SingleInvoice) o;
        return invoiceId == that.invoiceId &&
                levelUpPoints == that.levelUpPoints &&
                orderItems.equals(that.orderItems) &&
                orderTotalPrice.equals(that.orderTotalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, orderItems, orderTotalPrice, levelUpPoints);
    }


    // to string


    @Override
    public String toString() {
        return "SingleInvoice{" +
                "invoiceId=" + invoiceId +
                ", orderItems=" + orderItems +
                ", orderTotalPrice=" + orderTotalPrice +
                ", levelUpPoints=" + levelUpPoints +
                '}';
    }
}
