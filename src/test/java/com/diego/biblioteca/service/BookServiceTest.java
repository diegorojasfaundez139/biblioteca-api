package com.diego.biblioteca.service;

import com.diego.biblioteca.exception.BookNotFoundException;
import com.diego.biblioteca.model.Book;
import com.diego.biblioteca.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() {

        List<Book> books = List.of(
                new Book(
                        "Clean Code",
                        "Robert C. Martin",
                        2008,
                        "Programación"
                )
        );

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.findAll();

        assertEquals(1, result.size());
        assertEquals("Clean Code", result.get(0).getTitle());
    }

    @Test
    void shouldThrowExceptionIfBookDoesNotExist() {

        when(bookRepository.findById(999L))
                .thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.findById(999L)
        );

        assertEquals(
                "Book not found with id: 999",
                exception.getMessage()
        );
    }
}
