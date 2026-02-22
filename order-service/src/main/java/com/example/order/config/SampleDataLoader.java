package com.example.order.config;

import com.example.order.domain.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleDataLoader {

    @Bean
    CommandLineRunner loadSampleOrders(OrderRepository orderRepository) {
        return args -> {
            if (orderRepository.count() > 0) return;
            orderRepository.save(order(1L, "PROD-001", 2));
            orderRepository.save(order(1L, "PROD-002", 1));
            orderRepository.save(order(2L, "PROD-001", 3));
            orderRepository.save(order(2L, "PROD-003", 1));
            orderRepository.save(order(3L, "PROD-002", 2));
        };
    }

    private static Order order(Long userId, String productId, int quantity) {
        Order o = new Order();
        o.setUserId(userId);
        o.setProductId(productId);
        o.setQuantity(quantity);
        return o;
    }
}
