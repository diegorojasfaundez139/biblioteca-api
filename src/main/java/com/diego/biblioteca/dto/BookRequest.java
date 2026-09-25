package com.diego.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookRequest {

    @Schema(
            description = "Título del libro",
            example = "Clean Code"
    )
    @NotBlank(message = "El título es obligatorio")
    private String title;

    @Schema(
            description = "Autor del libro",
            example = "Robert C. Martin"
    )
    @NotBlank(message = "El autor es obligatorio")
    private String author;

    @Schema(
            description = "Año de publicación del libro",
            example = "2008",
            minimum = "1000"
    )
    @NotNull(message = "El año es obligatorio")
    @Min(value = 1000, message = "El año no es válido")
    private Integer year;

    @Schema(
            description = "Categoría del libro",
            example = "Programación"
    )
    @NotBlank(message = "La categoría es obligatoria")
    private String category;

    public BookRequest() {
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