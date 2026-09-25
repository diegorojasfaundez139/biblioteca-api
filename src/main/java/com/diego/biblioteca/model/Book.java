package com.diego.biblioteca.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único del libro",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Título del libro",
            example = "Clean Code"
    )
    private String title;

    @Schema(
            description = "Autor del libro",
            example = "Robert C. Martin"
    )
    private String author;

    @Schema(
            description = "Año de publicación del libro",
            example = "2008"
    )
    private Integer year;

    @Schema(
            description = "Categoría del libro",
            example = "Programación"
    )
    private String category;

    public Book() {
    }

    public Book(String title, String author, Integer year, String category) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}