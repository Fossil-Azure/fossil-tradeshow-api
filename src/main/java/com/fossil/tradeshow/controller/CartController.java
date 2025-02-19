package com.fossil.tradeshow.controller;

import com.fossil.tradeshow.model.Cart;
import com.fossil.tradeshow.model.CartItem;
import com.fossil.tradeshow.model.CartResponse;
import com.fossil.tradeshow.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add to cart
    @PostMapping("/{emailId}/add")
    public ResponseEntity<CartResponse> addToCart(@PathVariable String emailId, @RequestBody CartItem request) {
        CartResponse response = cartService.addToCart(
                emailId,
                request.getProduct(),
                request.getQuantity(),
                request.isConfirmAddition()
        );
        return ResponseEntity.ok(response);
    }

    // Add bulk items to cart
    @PostMapping("/{emailId}/add-bulk")
    public ResponseEntity<CartResponse> bulkAddToCart(
            @PathVariable String emailId,
            @RequestBody List<CartItem> cartItems,
            @RequestParam(defaultValue = "false") boolean confirmAddition) {

        CartResponse response = cartService.bulkAddToCart(emailId, cartItems, confirmAddition);
        return ResponseEntity.ok(response);
    }

    // Get cart endpoint
    @GetMapping("/{emailId}")
    public ResponseEntity<Cart> getCart(@PathVariable String emailId) {
        return ResponseEntity.ok(cartService.getCart(emailId));
    }

    @DeleteMapping("/{emailId}/item/{sku}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable String emailId, @PathVariable String sku) {
        try {
            cartService.removeItem(emailId, sku);
            return ResponseEntity.noContent().build(); // Return 204 No Content on success
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if cart or item not found
        }
    }

    @PutMapping("/{emailId}/item/{sku}/quantity/{quantity}")
    public ResponseEntity<Cart> updateCartItemQuantity(
            @PathVariable String emailId,
            @PathVariable String sku,
            @PathVariable int quantity
    ) {
        Cart updatedCart = cartService.updateItemQuantity(emailId, sku, quantity);
        return ResponseEntity.ok(updatedCart);
    }
}
