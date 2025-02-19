package com.fossil.tradeshow.model;

public class CartResponse {
    private boolean success;
    private String message;
    private Cart cart;

    public CartResponse(boolean success, String message, Cart cart) {
        this.success = success;
        this.message = message;
        this.cart = cart;
    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
