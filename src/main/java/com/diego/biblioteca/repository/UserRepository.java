package com.diego.biblioteca.repository;

import com.diego.biblioteca.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}