package com.chembase.service;

import com.chembase.model.User;
import com.chembase.repository.UserRepository;
import com.chembase.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String login(User user) {
        // Verkrijg username en password uit het User object
        String username = user.getUsername();
        String password = user.getPassword();

        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null && passwordEncoder.matches(password, existingUser.getPassword())) {
            String token = jwtTokenProvider.createToken(username);
            logLoginEvent(username, true);
            return token;
        }
        logLoginEvent(username, false);
        throw new RuntimeException("Invalid credentials");
    }

    private void logLoginEvent(String username, boolean success) {
        String message = String.format("User: %s, Login Success: %s", username, success);
        kafkaTemplate.send("login-events", message);
    }
}