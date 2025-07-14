package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        return ResponseEntity.ok(userRepo.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Optional<User> foundUser = userRepo.findByUsername(user.getUsername());
        if (foundUser.isPresent() && user.getPassword().equals(foundUser.get().getPassword())) {
            return ResponseEntity.ok("Login Successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        Optional<User> foundUser = userRepo.findById(id);
        if (foundUser.isPresent()){
            return ResponseEntity.ok(foundUser.get());
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with ID %d not found", id));
    }
}
