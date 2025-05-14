package com.example.skillshareeeeeeee.controllers;

import com.example.skillshareeeeeeee.dto.UserDto;
import com.example.skillshareeeeeeee.services.usersrvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/users")

public class UserDtoController {
    private usersrvc userService;
    @Autowired
    public UserDtoController(usersrvc userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
