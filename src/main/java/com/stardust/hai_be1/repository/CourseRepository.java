package com.stardust.hai_be1.repository;

import org.springframework.data.repository.CrudRepository;

import com.stardust.hai_be1.entity.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {
    
}
