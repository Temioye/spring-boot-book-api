package com.temioye.bookapi.controller;

import com.temioye.bookapi.dto.ApiResponse;
import com.temioye.bookapi.entity.Book;
import com.temioye.bookapi.exception.BookNotFoundException;
import com.temioye.bookapi.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal maxPrice) {

        log.info("GET /api/books called");

        List<Book> books;
        if (maxPrice != null) {
            books = bookService.getBooksByMaxPrice(maxPrice);
        } else if (category != null) {
            books = bookService.getBooksByCategory(category);
        } else {
            books = bookService.getAllBooks();
        }

        return ResponseEntity.ok(ApiResponse.success(books));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long id) {
        log.info("GET /api/books/{} called", id);
        try {
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok(ApiResponse.success(book));
        } catch (BookNotFoundException e) {
            log.error("Book not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> createBook(@RequestBody Book book) {
        log.info("POST /api/books called");
        Book saved = bookService.saveBook(book);
        return ResponseEntity.ok(ApiResponse.success(saved));
    }
}