package com.geetalibrary.backend.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record MemberRequest(
    @NotBlank @Size(max = 100) String name,
    @NotBlank @Pattern(regexp = "^[6-9][0-9]{9}$", message = "must be a valid 10-digit mobile number") String mobile,
    String address,
    String seatNumber,
    String planName,
    String joiningDate,
    String expiryDate,
    BigDecimal dueAmount,
    String status
) {}
