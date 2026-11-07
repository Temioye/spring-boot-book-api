package com.temioye.bookapi.entity;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Book {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)

    private Long id;

    private String title;
    private String author;
    private String category;
    private BigDecimal price;
}
