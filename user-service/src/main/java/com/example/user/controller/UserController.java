package com.example.user.controller;

import com.example.user.domain.User;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Value("${buildNumber:1.0.0}")
    private String buildNumber;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/version")
    public Map<String, String> version() {
        return Map.of("version", buildNumber);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.save(user);
    }
}
