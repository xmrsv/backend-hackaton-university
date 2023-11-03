package com.stardust.hai_be1.repository;

import org.springframework.data.repository.CrudRepository;

import com.stardust.hai_be1.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
