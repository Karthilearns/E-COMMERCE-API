package com.example.ecom.model;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id")
    private String id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "product_details")
    private String product_details;

    @Column(name = "category")
    private String category;

    @Column(name = "quantity_available")
    private String quantity_available;

    @Column(name="price")
    private String price;

    @Column(name="seller_email")
    private String seller_email;

    @Column(name = "seller_name")
    private  String seller_name;

    @Column(name = "shop_name")
    private String shop_name;

    @Column(name = "orders_placed")
    private int orders_placed;

    @Column(name = "status")
    private  String status;

    @Override
    public String toString() {
        return "Products{" +
                "id='" + id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_details='" + product_details + '\'' +
                ", category='" + category + '\'' +
                ", quantity_available='" + quantity_available + '\'' +
                ", price='" + price + '\'' +
                ", seller_email='" + seller_email + '\'' +
                ", seller_name='" + seller_name + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getProduct_details() {
        return product_details;
    }

    public void setProduct_details(String product_details) {
        this.product_details = product_details;
    }

    public String getId() {
        return id;
    }
    public int getOrders_placed() {
        return orders_placed;
    }

    public void setOrders_placed(int orders_placed) {
        this.orders_placed = orders_placed;
    }
    public void setId(String  id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(String quantity_available) {
        this.quantity_available = quantity_available;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
