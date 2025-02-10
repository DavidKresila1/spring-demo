package dev.kresila.spring.demo.service;

import dev.kresila.spring.demo.model.User;
import dev.kresila.spring.demo.repository.UserRepository;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String name, String surname, String email, String password) {
        User user = new User();

        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
