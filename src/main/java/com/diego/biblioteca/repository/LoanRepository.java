package com.diego.biblioteca.repository;

import com.diego.biblioteca.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
}