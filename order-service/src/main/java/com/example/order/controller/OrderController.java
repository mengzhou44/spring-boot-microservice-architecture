package com.example.order.controller;

import com.example.order.client.UserDto;
import com.example.order.domain.Order;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Value("${buildNumber:1.0.0}")
    private String buildNumber;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/version")
    public Map<String, String> version() {
        return Map.of("version", buildNumber);
    }

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Order> findByUserId(@PathVariable Long userId) {
        return orderService.findByUserId(userId);
    }

    /** Calls user-service via service discovery (Feign); use via gateway to test full chain. */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUserByUserId(@PathVariable Long userId) {
        return orderService.getUserForOrder(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return orderService.save(order);
    }
}
