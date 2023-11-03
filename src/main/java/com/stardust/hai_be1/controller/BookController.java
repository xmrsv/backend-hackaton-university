package com.stardust.hai_be1.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stardust.hai_be1.entity.Book;
import com.stardust.hai_be1.repository.BookRepository;

@RestController
public class BookController {
    private final BookRepository repository;
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    // List all books
    @GetMapping("/books")
    ResponseEntity<?> all() {
        logger.info("Listing all books");
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<?> newBook(@RequestParam("image") MultipartFile image, @RequestParam("title") String title,
            @RequestParam("author") String author) {
        logger.info("Creating new book with title: " + title);
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);

        try {
            // Crear la carpeta 'images' si no existe
            Files.createDirectories(Paths.get("images"));

            // Guardar la imagen en el servidor
            Path path = Paths.get("images/" + image.getOriginalFilename());
            Files.write(path, image.getBytes());

            // Guardar la ruta de la imagen en la base de datos
            newBook.setImagePath(path.toString());
        } catch (IOException e) {
            logger.error("Error processing image", e);
            return new ResponseEntity<>("Error processing image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        repository.save(newBook);
        logger.info("Book saved successfully with id: " + newBook.getId());
        return new ResponseEntity<>("Book saved successfully", HttpStatus.CREATED);
    }

    // List book by ID
    @GetMapping("/books/{id}")
    ResponseEntity<?> one(@PathVariable Long id) {
        logger.info("Searching for book with id " + id);
        Optional<Book> bookOptional = repository.findById(id);
        if (bookOptional.isPresent()) {
            return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }

    // Update book by ID
    @PutMapping("/books/{id}")
    ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book newBook) {
        return repository.findById(id)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setAuthor(newBook.getAuthor());
                    repository.save(book);
                    return ResponseEntity.ok(book);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    repository.save(newBook);
                    return ResponseEntity.ok(newBook);
                });
    }

    // Delete book
    @DeleteMapping("/books/{id}")
    ResponseEntity<?> deleteBook(@PathVariable Long id) {
        logger.info("Deleting book with id " + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }
}
