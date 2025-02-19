package com.fossil.tradeshow.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    @Id
    String emailId;

    // List of items in the cart
    private List<CartItem> items;

    // Default constructor
    public Cart() {}

    // Parameterized constructor
    public Cart(String emailId, List<CartItem> items, double totalPrice) {
        this.emailId = emailId;
        this.items = items;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
