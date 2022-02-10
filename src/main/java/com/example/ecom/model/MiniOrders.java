package com.example.ecom.model;

public class MiniOrders {
    String customer_address;
    String customer_name;
    String product_name;
    String quantity;
    String date;

    @Override
    public String toString() {
        return "MiniOrders{" +
                "customer_address='" + customer_address + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", product_name='" + product_name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
