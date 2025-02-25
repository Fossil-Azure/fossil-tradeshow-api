package com.fossil.tradeshow.service;

import com.fossil.tradeshow.model.Cart;
import com.fossil.tradeshow.model.CartItem;
import com.fossil.tradeshow.model.CartResponse;
import com.fossil.tradeshow.model.Product;
import com.fossil.tradeshow.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // Add a product to the cart
    public CartResponse addToCart(String emailId, Product product, int quantity, boolean confirmAddition) {
        // Fetch the cart or create a new one
        Cart cart = Optional.ofNullable(cartRepository.findByEmailId(emailId))
                .orElse(new Cart(emailId, new ArrayList<>(), 0));

        // Check if the product already exists in the cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getSku().equals(product.getSku()))
                .findFirst();

        if (existingItem.isPresent()) {
            if (!confirmAddition) {
                // Product exists, but addition not confirmed
                return new CartResponse(false, "Product already exists in the cart.", cart);
            } else {
                // Add the quantity if confirmed
                CartItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + quantity);
            }
        } else {
            // Add new product to the cart
            cart.getItems().add(new CartItem(product, quantity));
        }

        // Save the cart
        Cart updatedCart = cartRepository.save(cart);

        // Return success response
        return new CartResponse(true, "Product added to the cart.", updatedCart);
    }

    // Bulk add items to cart
    public CartResponse bulkAddToCart(String emailId, List<CartItem> cartItems, boolean confirmAddition) {
        // Fetch the cart once to avoid multiple DB queries
        Cart cart = cartRepository.findByEmailId(emailId);
        if (cart == null) {
            cart = new Cart(emailId, new ArrayList<>(), 0);
        }

        boolean cartUpdated = false;
        List<String> itemsNeedingConfirmation = new ArrayList<>();

        for (CartItem newItem : cartItems) {
            String newSku = newItem.getProduct().getSku();

            Optional<CartItem> existingItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getSku().trim().equalsIgnoreCase(newSku.trim()))
                    .findFirst();

            if (existingItem.isPresent()) {
                if (!confirmAddition) {
                    // Add to confirmation list
                    itemsNeedingConfirmation.add(newSku);
                } else {
                    // User confirmed, add quantity
                    existingItem.get().setQuantity(existingItem.get().getQuantity() + newItem.getQuantity());
                    cartUpdated = true;
                }
            } else {
                // New item, add to cart
                cart.getItems().add(new CartItem(newItem.getProduct(), newItem.getQuantity()));
                cartUpdated = true;
            }
        }

        // If confirmation is needed, return response without updating the cart
        if (!itemsNeedingConfirmation.isEmpty()) {
            return new CartResponse(false,
                    "The following items already exist in the cart: " + String.join(", ", itemsNeedingConfirmation)
                            + ". Do you want to add more?", cart);
        }

        // Save cart only if updated
        if (cartUpdated) {
            cart = cartRepository.save(cart);
        }

        return new CartResponse(true, "All items added successfully.", cart);
    }


    // Get the cart by ID
    public Cart getCart(String emailId) {
        return cartRepository.findByEmailId(emailId);
    }

    public void removeItem(String emailId, String sku) {
        // Fetch the cart for the user
        Cart cart = cartRepository.findByEmailId(emailId);

        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for email: " + emailId);
        }

        // Filter out the item to be removed
        cart.setItems(cart.getItems().stream()
                .filter(item -> !item.getProduct().getSku().equals(sku))
                .collect(Collectors.toList()));

        // Save the updated cart
        cartRepository.save(cart);
    }

    public Cart updateItemQuantity(String emailId, String sku, int quantity) {
        // Fetch the user's cart
        Cart cart = cartRepository.findByEmailId(emailId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for email: " + emailId);
        }

        // Update the quantity for the specific item
        cart.getItems().forEach(item -> {
            if (item.getProduct().getSku().equals(sku)) {
                item.setQuantity(quantity);
            }
        });

        // Save the updated cart
        return cartRepository.save(cart);
    }

}