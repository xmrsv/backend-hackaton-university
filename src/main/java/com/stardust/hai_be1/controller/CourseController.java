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

import com.stardust.hai_be1.entity.Course;
import com.stardust.hai_be1.repository.CourseRepository;

@RestController
public class CourseController {
    private final CourseRepository repository;
    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/courses")
    ResponseEntity<?> all() {
        logger.info("Listing all courses");
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/courses")
    ResponseEntity<?> newCourse(@RequestBody Course newCourse) {
        logger.info("Saving course");
        repository.save(newCourse);
        return new ResponseEntity<>("Course saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/courses/{id}")
    ResponseEntity<?> one(@PathVariable Long id) {
        logger.info("Searching for course with id " + id);
        Optional<Course> cursoOptional = repository.findById(id);
        if (cursoOptional.isPresent()) {
            return new ResponseEntity<>(cursoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND); // Cambiado a Course not found
        }
    }

    @DeleteMapping("/courses/{id}")
    ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        logger.info("Deleting course with id " + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
    }
}
