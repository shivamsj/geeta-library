package com.geetalibrary.backend.auth;

import com.geetalibrary.backend.auth.dto.AuthResponse;
import com.geetalibrary.backend.auth.dto.ForgotPasswordRequest;
import com.geetalibrary.backend.auth.dto.LoginRequest;
import com.geetalibrary.backend.auth.dto.MessageResponse;
import com.geetalibrary.backend.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.geetalibrary.backend.user.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) { this.service = service; }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) { return service.register(request); }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) { return service.login(request); }

    @PostMapping("/forgot-password")
    public MessageResponse forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        service.requestPasswordReset(request.email());
        return new MessageResponse("Password reset request accepted");
    }

    @GetMapping("/me")
    public AuthResponse currentUser(@AuthenticationPrincipal User user) {
        return service.currentUser(user);
    }
}
