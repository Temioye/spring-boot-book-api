package com.temioye.bookapi.service;

import com.temioye.bookapi.entity.Book;
import com.temioye.bookapi.exception.BookNotFoundException;
import com.temioye.bookapi.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    @Value("${app.name}")
    private String appName;

    public List<Book> getAllBooks() {
        log.info("Getting all books from {}", appName);
        return bookRepository.findAll();
    }

    public List<Book> getBooksByCategory(String category) {
        log.debug("Searching books in category: {}", category);
        return bookRepository.findByCategory(category);
    }


    public Book getBookById(Long id) throws BookNotFoundException {
        log.debug("Getting book by id: {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }


    public Book saveBook(Book book) {
        try {
            log.info("Saving new book: {}", book.getTitle());
            return bookRepository.save(book);
        } catch (Exception e) {
            log.error("Error saving book: {}", e.getMessage());
            throw e;
        }
    }

    @PostConstruct
    public void testConnection() {
        log.info("Testing database connection...");
        List<Book> books = bookRepository.findAll();
        log.info("Found {} books in database", books.size());
        books.forEach(book -> log.info("Book: {}", book.getTitle()));
    }

    public List<Book> getBooksByMaxPrice(BigDecimal maxPrice) {
        return List.of();
    }
}