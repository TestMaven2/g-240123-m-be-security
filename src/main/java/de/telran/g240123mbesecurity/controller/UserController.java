package de.telran.g240123mbesecurity.controller;

import de.telran.g240123mbesecurity.domain.entity.User;
import de.telran.g240123mbesecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService service;

    // Этот метод доступен даже не аутентифицированным пользователям
    @GetMapping(value = "/all")
    public List<User> getAll() {
        return service.getAll();
    }

    // Этот метод доступен аутентифицированным пользователям и администраторам
    @GetMapping(value = "/name/{name}")
    public UserDetails getByName(@PathVariable String name) {
        return service.loadUserByUsername(name);
    }

    // Этот метод доступен только аутентифицированным администраторам
    @PostMapping(value = "/add")
    public User add(@RequestBody User user) {
        return service.saveUser(user);
    }
}