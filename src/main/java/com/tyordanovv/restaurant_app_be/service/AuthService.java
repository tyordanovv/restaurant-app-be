package com.tyordanovv.restaurant_app_be.service;
import com.tyordanovv.restaurant_app_be.model.User;
import com.tyordanovv.restaurant_app_be.security.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final List<User> users = new ArrayList<>();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtTokenProvider jwtTokenProvider;
    private Long idCounter = 1L;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String register(String email, String password) {
        User newUser = new User(
                idCounter++,
                email,
                passwordEncoder.encode(password)
        );
        users.add(newUser);
        return "User registered successfully";
    }

    public String login(String email, String password) {
        Optional<User> user = users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return jwtTokenProvider.generateToken(email, user.get().getId());
        }

        throw new RuntimeException("Invalid credentials");
    }
}