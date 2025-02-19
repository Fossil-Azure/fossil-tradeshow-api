package com.fossil.tradeshow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fossil.tradeshow.model.Orders;
import com.fossil.tradeshow.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService orderService;

    // Place a new order
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(
            @RequestBody Orders order,
            @RequestParam(required = false, defaultValue = "false") boolean confirm) throws JsonProcessingException {
        try {
            Orders savedOrder = orderService.placeOrder(order, confirm);
            return ResponseEntity.ok(savedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Get orders by user email
    @GetMapping("/user/{email}")
    public List<Orders> getUserOrders(@PathVariable String email) {
        return orderService.getOrdersByUser(email);
    }

    // Update an existing order
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(
            @PathVariable String id,
            @RequestBody Orders updatedOrder) {
        try {
            Orders existingOrder = orderService.getOrderById(id);
            if (existingOrder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
            }

            updatedOrder.setId(existingOrder.getId());
            updatedOrder.setEmailId(existingOrder.getEmailId());

            Orders savedOrder = orderService.updateOrder(updatedOrder);
            System.out.print(savedOrder);
            return ResponseEntity.ok(savedOrder);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
