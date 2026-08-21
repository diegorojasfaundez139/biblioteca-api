package com.diego.biblioteca.controller;

import com.diego.biblioteca.dto.LoanRequest;
import com.diego.biblioteca.model.Loan;
import com.diego.biblioteca.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {

        return loanService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Loan createLoan(
            @Valid @RequestBody LoanRequest request) {

        return loanService.createLoan(
                request.getUserId(),
                request.getBookId()
        );
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Loan> returnBook(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                loanService.returnBook(id)
        );
    }
}