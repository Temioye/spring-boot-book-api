package com.temioye.bookapi.controller;

import com.temioye.bookapi.entity.Book;
import com.temioye.bookapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        // Given
        Book book1 = new Book(1L, "Test Book", "Test Author", "Test", new BigDecimal("10.99"));
        Book book2 = new Book(2L, "Another Book", "Another Author", "Test", new BigDecimal("15.99"));
        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(books);

        // When
        var response = bookController.getAllBooks(null, null);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void getAllBooks_WithMaxPrice_ShouldFilterBooks() {
        // Given
        Book book1 = new Book(1L, "Cheap Book", "Author", "Test", new BigDecimal("10.99"));
        List<Book> books = Arrays.asList(book1);

        when(bookService.getBooksByMaxPrice(new BigDecimal("12.00"))).thenReturn(books);

        // When
        var response = bookController.getAllBooks(null, new BigDecimal("12.00"));

        // Then
        assertEquals(1, response.getBody().getData().size());
        assertEquals("Cheap Book", response.getBody().getData().get(0).getTitle());
    }

    @Test
    void getAllBooks_WhenEmpty_ShouldReturnEmptyList() {
        // Given - boundary test
        when(bookService.getAllBooks()).thenReturn(Arrays.asList());

        // When
        var response = bookController.getAllBooks(null, null);

        // Then
        assertEquals(0, response.getBody().getData().size());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void getBookById_WhenExists_ShouldReturnBook() throws Exception {
        // Given
        Book book = new Book(1L, "Test Book", "Test Author", "Test", new BigDecimal("10.99"));
        when(bookService.getBookById(1L)).thenReturn(book);

        // When
        var response = bookController.getBookById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Test Book", response.getBody().getData().getTitle());
    }

    @Test
    void getBookById_WhenNotExists_ShouldReturnError() throws Exception {
        // Given - invalid input test
        when(bookService.getBookById(999L))
                .thenThrow(new com.temioye.bookapi.exception.BookNotFoundException("Book not found with id: 999"));

        // When
        var response = bookController.getBookById(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Book not found with id: 999", response.getBody().getMessage());
    }

    @Test
    void createBook_WithValidData_ShouldReturnCreatedBook() {
        // Given
        Book book = new Book(null, "New Book", "New Author", "Fiction", new BigDecimal("20.00"));
        Book savedBook = new Book(1L, "New Book", "New Author", "Fiction", new BigDecimal("20.00"));

        when(bookService.saveBook(any(Book.class))).thenReturn(savedBook);

        // When
        var response = bookController.createBook(book);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertNotNull(response.getBody().getData().getId());
        verify(bookService, times(1)).saveBook(any(Book.class));
    }
}