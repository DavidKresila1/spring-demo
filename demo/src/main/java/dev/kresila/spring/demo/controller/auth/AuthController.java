package dev.kresila.spring.demo.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import dev.kresila.spring.demo.service.UserService;
import dev.kresila.spring.demo.model.User;
import dev.kresila.spring.demo.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;  

    public AuthController(UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(String name, String surname, String email, String password) {
        userService.registerUser(name, surname, email, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String token = JwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(token);
    }
}
