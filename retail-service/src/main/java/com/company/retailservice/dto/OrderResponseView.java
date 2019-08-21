package com.company.retailservice.dto;

import java.util.List;
import java.util.Objects;

public class OrderResponseView {


    private Customer customer;
    private SingleInvoice order;


    // getters / setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SingleInvoice getOrder() {
        return order;
    }

    public void setOrder(SingleInvoice order) {
        this.order = order;
    }
// equals / hash

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderResponseView that = (OrderResponseView) o;
        return customer.equals(that.customer) &&
                order.equals(that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, order);
    }


    // to String

    @Override
    public String toString() {
        return "OrderResponseView{" +
                "customer=" + customer +
                ", order=" + order +
                '}';
    }
}
