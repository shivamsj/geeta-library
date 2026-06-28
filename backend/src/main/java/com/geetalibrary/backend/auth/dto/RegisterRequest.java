package com.geetalibrary.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank @Size(max = 100) String name,
    @NotBlank @Pattern(regexp = "^[6-9][0-9]{9}$", message = "must be a valid 10-digit mobile number") String mobile,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 6, max = 72) String password
) {}
