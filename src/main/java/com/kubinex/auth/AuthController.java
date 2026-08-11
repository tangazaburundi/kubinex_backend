package com.kubinex.auth;

import com.kubinex.user.User;
import com.kubinex.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public AuthController(JwtUtil jwtUtil, PasswordEncoder encoder, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        User user = userRepository.findByUsername(req.username()).orElse(null);
        if (user == null || !encoder.matches(req.password(), user.getPassword())) {
            return ResponseEntity.status(401).body(new ErrorResponse("Invalid credentials"));
        }
        String token = jwtUtil.generate(user.getUsername(), user.getRole().name());
        return ResponseEntity.ok(new LoginResponse(token, user.getUsername(), user.getRole().name()));
    }

    record LoginRequest(@NotBlank String username, @NotBlank String password) {}
    record LoginResponse(String token, String username, String role) {}
    record ErrorResponse(String message) {}
}
