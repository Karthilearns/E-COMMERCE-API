package com.example.ecom.model;

import javax.persistence.*;

@Entity
@Table(name = "Seller_Profile")
public class Seller {

    @Column(name = "email")
    @Id
    String email;

    @Column(name = "shop_name")
    String shop_name;

    @Column(name = "short_description")
    String short_description;

    @Column(name = "pincode")
    String pincode;

    @Column(name = "phone_number")
    String phone_number;

    @Column(name = "phone_verified")
    String phone_verified;

    @Column(name = "email_verified")
    String email_verified;

    @Column(name = "seller_name")
    String seller_name;

    @Column(name = "password")
    String password;

    @Column(name = "token")
    String token;

    @Column(name="category")
    String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhone_verified() {
        return phone_verified;
    }

    public void setPhone_verified(String phone_verified) {
        this.phone_verified = phone_verified;
    }

    public String getEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(String email_verified) {
        this.email_verified = email_verified;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "email='" + email + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", short_description='" + short_description + '\'' +
                ", pincode='" + pincode + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", phone_verified='" + phone_verified + '\'' +
                ", email_verfied='" + email_verified + '\'' +
                ", seller_name='" + seller_name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
