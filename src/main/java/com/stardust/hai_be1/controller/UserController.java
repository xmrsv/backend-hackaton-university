package com.stardust.hai_be1.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stardust.hai_be1.entity.User;
import com.stardust.hai_be1.repository.UserRepository;

@RestController
public class UserController {

    private final UserRepository repository;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    // List all users
    @GetMapping("/users")
    ResponseEntity<?> all() {
        logger.info("Listing all users");
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    // Save/Update user
    @PostMapping("/users")
    ResponseEntity<?> newUser(@RequestBody User newUser) {
        logger.info("Saving user");
        repository.save(newUser);
        return new ResponseEntity<>("User saved successfully", HttpStatus.CREATED);
    }

    // List user by ID
    @GetMapping("/users/{id}")
    ResponseEntity<?> one(@PathVariable Long id) {
        logger.info("Searching for user with id " + id);
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with id " + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
