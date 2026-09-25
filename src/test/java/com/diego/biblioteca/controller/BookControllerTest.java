package com.diego.biblioteca.controller;

import com.diego.biblioteca.exception.BookNotFoundException;
import com.diego.biblioteca.model.Book;
import com.diego.biblioteca.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() throws Exception {

        List<Book> books = List.of(
                new Book(
                        "Clean Code",
                        "Robert C. Martin",
                        2008,
                        "Programación"
                )
        );

        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title")
                        .value("Clean Code"))
                .andExpect(jsonPath("$[0].author")
                        .value("Robert C. Martin"));
    }

    @Test
    void shouldReturnBookById() throws Exception {

        Book book = new Book(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "Programación"
        );

        when(bookService.findById(1L)).thenReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("Clean Code"))
                .andExpect(jsonPath("$.author")
                        .value("Robert C. Martin"));
    }

    @Test
    void shouldReturn404IfBookDoesNotExist() throws Exception {

        when(bookService.findById(999L))
                .thenThrow(
                        new BookNotFoundException(
                                "Book not found with id: 999"
                        )
                );

        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("Book not found with id: 999"));
    }

    @Test
    void shouldReturn400IfBookDataIsInvalid() throws Exception {

        String json = """
                {
                    "title": "",
                    "author": "",
                    "year": 500,
                    "category": ""
                }
                """;

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .post("/books")
                                .contentType("application/json")
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.title")
                        .value("El título es obligatorio"))
                .andExpect(jsonPath("$.errors.author")
                        .value("El autor es obligatorio"))
                .andExpect(jsonPath("$.errors.year")
                        .value("El año no es válido"))
                .andExpect(jsonPath("$.errors.category")
                        .value("La categoría es obligatoria"));
    }
    @Test
    void shouldCreateBook() throws Exception {

        Book savedBook = new Book(
                "Effective Java",
                "Joshua Bloch",
                2018,
                "Programación"
        );

        when(bookService.save(any(Book.class)))
                .thenReturn(savedBook);

        String json = """
            {
                "title": "Effective Java",
                "author": "Joshua Bloch",
                "year": 2018,
                "category": "Programación"
            }
            """;

        mockMvc.perform(
                        post("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("Effective Java"))
                .andExpect(jsonPath("$.author")
                        .value("Joshua Bloch"))
                .andExpect(jsonPath("$.year")
                        .value(2018))
                .andExpect(jsonPath("$.category")
                        .value("Programación"));

        verify(bookService).save(any(Book.class));
    }
    @Test
    void shouldUpdateBook() throws Exception {

        Book existingBook = new Book(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "Programación"
        );

        when(bookService.findById(1L))
                .thenReturn(existingBook);

        when(bookService.save(any(Book.class)))
                .thenReturn(existingBook);

        String json = """
            {
                "title": "Clean Code - Updated",
                "author": "Robert C. Martin",
                "year": 2008,
                "category": "Programación"
            }
            """;

        mockMvc.perform(
                        put("/books/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("Clean Code - Updated"))
                .andExpect(jsonPath("$.author")
                        .value("Robert C. Martin"))
                .andExpect(jsonPath("$.year")
                        .value(2008))
                .andExpect(jsonPath("$.category")
                        .value("Programación"));

        verify(bookService).findById(1L);
        verify(bookService).save(existingBook);
    }

    @Test
    void shouldDeleteBook() throws Exception {

        Book existingBook = new Book(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "Programación"
        );

        when(bookService.findById(1L))
                .thenReturn(existingBook);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService).findById(1L);
        verify(bookService).deleteById(1L);
    }

    @Test
    void shouldSearchBooksByTitle() throws Exception {

        List<Book> books = List.of(
                new Book(
                        "Clean Code",
                        "Robert C. Martin",
                        2008,
                        "Programación"
                )
        );

        when(bookService.searchByTitle("Clean"))
                .thenReturn(books);

        mockMvc.perform(
                        get("/books/search")
                                .param("title", "Clean")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title")
                        .value("Clean Code"))
                .andExpect(jsonPath("$[0].author")
                        .value("Robert C. Martin"));

        verify(bookService).searchByTitle("Clean");
    }

    @Test
    void shouldSearchBooksByAuthor() throws Exception {

        List<Book> books = List.of(
                new Book(
                        "Clean Code",
                        "Robert C. Martin",
                        2008,
                        "Programación"
                )
        );

        when(bookService.searchByAuthor("Robert"))
                .thenReturn(books);

        mockMvc.perform(
                        get("/books/search")
                                .param("author", "Robert")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title")
                        .value("Clean Code"))
                .andExpect(jsonPath("$[0].author")
                        .value("Robert C. Martin"));

        verify(bookService).searchByAuthor("Robert");
    }
}