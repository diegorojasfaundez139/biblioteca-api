package com.diego.biblioteca.service;

import com.diego.biblioteca.model.Book;
import com.diego.biblioteca.model.Loan;
import com.diego.biblioteca.model.User;
import com.diego.biblioteca.repository.BookRepository;
import com.diego.biblioteca.repository.LoanRepository;
import com.diego.biblioteca.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(
            LoanRepository loanRepository,
            UserRepository userRepository,
            BookRepository bookRepository) {

        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id);
    }

    public Loan createLoan(Long userId, Long bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new RuntimeException("Libro no encontrado"));

        if (loanRepository.existsByBookIdAndReturnDateIsNull(bookId)) {
            throw new RuntimeException("El libro ya está prestado");
        }

        Loan loan = new Loan();

        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());

        return loanRepository.save(loan);
    }

    public Loan returnBook(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Préstamo no encontrado"));

        loan.setReturnDate(LocalDate.now());

        return loanRepository.save(loan);
    }
}