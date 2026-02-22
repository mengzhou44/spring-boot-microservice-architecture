package com.example.order.service;

import com.example.order.client.UserClient;
import com.example.order.client.UserDto;
import com.example.order.domain.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;

    public OrderService(OrderRepository orderRepository, UserClient userClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Optional<UserDto> getUserForOrder(Long userId) {
        try {
            return Optional.of(userClient.getUserById(userId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
