package com.geetalibrary.backend.auth;

import com.geetalibrary.backend.auth.dto.AuthResponse;
import com.geetalibrary.backend.auth.dto.LoginRequest;
import com.geetalibrary.backend.auth.dto.RegisterRequest;
import com.geetalibrary.backend.security.JwtService;
import com.geetalibrary.backend.user.Role;
import com.geetalibrary.backend.user.User;
import com.geetalibrary.backend.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository users, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (users.existsByEmailIgnoreCase(request.email())) throw new IllegalArgumentException("Email is already registered");
        if (users.existsByMobile(request.mobile())) throw new IllegalArgumentException("Mobile number is already registered");
        User user = users.save(new User(
            request.name().trim(), request.mobile().trim(), request.email().trim().toLowerCase(),
            passwordEncoder.encode(request.password()), Role.ADMIN));
        return response(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email().trim().toLowerCase(), request.password()));
        User user = users.findByEmailIgnoreCase(request.email())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return response(user);
    }

    public void requestPasswordReset(String email) {
        // Keep the response generic. SMTP-backed reset tokens are the next security module.
        users.findByEmailIgnoreCase(email).ifPresent(user -> {});
    }

    public AuthResponse currentUser(User user) {
        return response(user);
    }

    private AuthResponse response(User user) {
        return new AuthResponse(jwtService.generate(user), "Bearer", user.getId().toString(),
            user.getName(), user.getEmail(), user.getRole().name());
    }
}
