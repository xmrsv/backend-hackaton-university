package com.stardust.hai_be1.repository;

import org.springframework.data.repository.CrudRepository;

import com.stardust.hai_be1.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
