package com.fossil.tradeshow.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartItem {

    // The product associated with this item
    private Product product;

    // Quantity of the product in the cart
    private int quantity;

    private boolean confirmAddition;

    // Default constructor
    public CartItem() {}

    // Parameterized constructor
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isConfirmAddition() {
        return confirmAddition;
    }

    public void setConfirmAddition(boolean confirmAddition) {
        this.confirmAddition = confirmAddition;
    }

    public double getSubtotalHkd() {
        if (product != null) {
            BigDecimal subtotal = BigDecimal.valueOf(product.getMrpHkd() * quantity)
                    .setScale(2, RoundingMode.HALF_UP);
            return subtotal.doubleValue();
        }
        return 0.0;
    }
}
