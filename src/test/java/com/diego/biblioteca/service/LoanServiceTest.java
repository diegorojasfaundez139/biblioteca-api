package com.diego.biblioteca.service;

import com.diego.biblioteca.model.Book;
import com.diego.biblioteca.model.User;
import com.diego.biblioteca.repository.BookRepository;
import com.diego.biblioteca.repository.LoanRepository;
import com.diego.biblioteca.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    void shouldNotLoanBookIfAlreadyLoaned() {

        User user = new User(
                "Juan Pérez",
                "juan@example.com"
        );

        Book book = new Book(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "Programación"
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        when(loanRepository.existsByBookIdAndReturnDateIsNull(1L))
                .thenReturn(true);

        assertThrows(
                RuntimeException.class,
                () -> loanService.createLoan(1L, 1L)
        );
    }
}