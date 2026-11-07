package com.temioye.bookapi.repository;

import com.temioye.bookapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByCategory(String category);
}
