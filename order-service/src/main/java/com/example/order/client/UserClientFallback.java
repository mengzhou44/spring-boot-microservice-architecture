package com.example.order.client;

import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {

    @Override
    public UserDto getUserById(Long id) {
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setName("(user service temporarily unavailable)");
        dto.setEmail("");
        return dto;
    }
}