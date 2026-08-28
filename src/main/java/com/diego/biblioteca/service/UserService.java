package com.diego.biblioteca.service;

import com.diego.biblioteca.exception.UserNotFoundException;
import com.diego.biblioteca.model.User;
import com.diego.biblioteca.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Usuario no encontrado");
        }

        userRepository.deleteById(id);
    }
}