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

import com.stardust.hai_be1.entity.Teacher;
import com.stardust.hai_be1.repository.TeacherRepository;

@RestController
public class TeacherController {
    private final TeacherRepository repository;
    private Logger logger = LoggerFactory.getLogger(TeacherController.class);

    TeacherController(TeacherRepository repository) {
        this.repository = repository;
    }

    // List all users
    @GetMapping("/teachers")
    ResponseEntity<?> all() {
        logger.info("Listing all teachers");
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    // Save/Update user
    @PostMapping("/teachers")
    ResponseEntity<?> newTeacher(@RequestBody Teacher newTeacher) {
        logger.info("Saving teacher");
        repository.save(newTeacher);
        return new ResponseEntity<>("teacher saved successfully", HttpStatus.CREATED);
    }

    // List user by ID
    @GetMapping("/teachers/{id}")
    ResponseEntity<?> one(@PathVariable Long id) {
        logger.info("Searching for teacher with id " + id);
        Optional<Teacher> teacherOpcional = repository.findById(id);
        if (teacherOpcional.isPresent()) {
            return new ResponseEntity<>(teacherOpcional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Teacher not found", HttpStatus.NOT_FOUND);
        }
    }

    // Delete user
    @DeleteMapping("/teachers/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        logger.info("Deleting teacher with id " + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>("Teacher deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Teacher not found", HttpStatus.NOT_FOUND);
        }
    }
}
