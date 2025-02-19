package com.fossil.tradeshow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fossil.tradeshow.model.CartItem;
import com.fossil.tradeshow.model.Orders;
import com.fossil.tradeshow.repository.CartRepository;
import com.fossil.tradeshow.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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

        System.out.println(emailData);

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
        return orderRepository.save(updatedOrder);
    }
}
