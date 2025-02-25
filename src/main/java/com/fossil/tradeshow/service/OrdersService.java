package com.fossil.tradeshow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fossil.tradeshow.model.CartItem;
import com.fossil.tradeshow.model.OrderChange;
import com.fossil.tradeshow.model.Orders;
import com.fossil.tradeshow.repository.CartRepository;
import com.fossil.tradeshow.repository.OrdersRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private EmailService emailService;

    public Orders placeOrder(Orders order, boolean forceConfirm) throws JsonProcessingException {
        // Get list of SKUs from the new order
        List<String> newOrderSkus = order.getItems().stream()
                .map(item -> item.getProduct().getSku())
                .toList();

        // Check if any SKU already exists in previous orders for the user
        List<Orders> existingOrders = orderRepository.findByEmailId(order.getEmailId());

        boolean hasDuplicateSKU = existingOrders.stream()
                .flatMap(existingOrder -> existingOrder.getItems().stream())
                .map(existingItem -> existingItem.getProduct().getSku())
                .anyMatch(newOrderSkus::contains);

        // If a duplicate SKU exists and user hasn't confirmed, return null or an error message
        if (hasDuplicateSKU && !forceConfirm) {
            throw new RuntimeException("Duplicate SKU detected. Please confirm before placing the order.");
        }

        // Set Order Date
        order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Save Order in the Database
        Orders savedOrder = orderRepository.save(order);

        for (CartItem item : savedOrder.getItems()) {
            item.getSubtotalHkd();
        }

        Map<String, Object> emailData = Map.of(
                "orderId", savedOrder.getId(),
                "orderDate", savedOrder.getOrderDate(),
                "totalUsd", savedOrder.getTotalUsd(),
                "totalHkd", savedOrder.getTotalHkd(),
                "totalAud", savedOrder.getTotalAud(),
                "totalInr", savedOrder.getTotalInr(),
                "totalSgd", savedOrder.getTotalSgd(),
                "totalJpy", savedOrder.getTotalJpy(),
                "orderItems", savedOrder.getItems()
        );

        emailService.sendOrderConfirmationEmail(savedOrder.getEmailId(), "Order Confirmation", emailData);

        // Remove ordered items from the cart
        cartRepository.deleteByEmailId(order.getEmailId());

        return savedOrder;
    }

    public List<Orders> getOrdersByUser(String userEmail) {
        return orderRepository.findByEmailId(userEmail);
    }

    public Orders updateOrderStatus(String orderId, String status) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderRepository.save(order);
    }

    public Orders getOrderById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Orders updateOrder(Orders updatedOrder) {
        Orders existingOrder = orderRepository.findById(updatedOrder.getId()).orElse(null);

        if (existingOrder == null) {
            throw new RuntimeException("Order not found!");
        }

        // Save the updated order
        Orders savedOrder = orderRepository.save(updatedOrder);

        List<OrderChange> changes = detectOrderChanges(existingOrder, updatedOrder);

        // Trigger email only if there are changes
        if (!changes.isEmpty()) {
            try {
                emailService.sendOrderUpdateEmail(updatedOrder.getEmailId(), changes);
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Error sending email: " + e.getMessage());
            }
        }
        return savedOrder;
    }

    private List<OrderChange> detectOrderChanges(Orders oldOrder, Orders newOrder) {
        List<OrderChange> changes = new ArrayList<>();

        // Track all SKUs (to ensure we detect added, removed, and unchanged items)
        Set<String> allSkus = new HashSet<>();

        // Add all SKUs from old and new order
        oldOrder.getItems().forEach(item -> allSkus.add(item.getProduct().getSku()));
        newOrder.getItems().forEach(item -> allSkus.add(item.getProduct().getSku()));

        // Compare items for changes
        for (String sku : allSkus) {
            CartItem oldItem = oldOrder.getItems().stream()
                    .filter(item -> item.getProduct().getSku().equals(sku))
                    .findFirst()
                    .orElse(null);

            CartItem newItem = newOrder.getItems().stream()
                    .filter(item -> item.getProduct().getSku().equals(sku))
                    .findFirst()
                    .orElse(null);

            if (oldItem == null && newItem != null) {
                // ✅ Added item
                changes.add(new OrderChange(
                        newItem.getProduct().getProductTitle(),
                        newItem.getProduct().getSku(),
                        0, newItem.getQuantity(),
                        "Added",
                        newItem.getProduct().getImageUrl(),
                        "green"
                ));
            } else if (oldItem != null && newItem == null) {
                // ✅ Removed item
                changes.add(new OrderChange(
                        oldItem.getProduct().getProductTitle(),
                        oldItem.getProduct().getSku(),
                        oldItem.getQuantity(), 0, // Old quantity exists, new quantity is 0
                        "Removed",
                        oldItem.getProduct().getImageUrl(),
                        "red"
                ));
            } else if (oldItem != null && newItem != null) {
                if (oldItem.getQuantity() != newItem.getQuantity()) {
                    // ✅ Updated item
                    String highlightClass = newItem.getQuantity() > oldItem.getQuantity() ? "green" : "red";
                    changes.add(new OrderChange(
                            newItem.getProduct().getProductTitle(),
                            newItem.getProduct().getSku(),
                            oldItem.getQuantity(), newItem.getQuantity(),
                            "Updated",
                            newItem.getProduct().getImageUrl(),
                            highlightClass
                    ));
                } else {
                    // ✅ Unchanged item
                    changes.add(new OrderChange(
                            newItem.getProduct().getProductTitle(),
                            newItem.getProduct().getSku(),
                            oldItem.getQuantity(), newItem.getQuantity(),
                            "Unchanged",
                            newItem.getProduct().getImageUrl(),
                            "black"
                    ));
                }
            }
        }
        return changes;
    }

}
