package com.geetalibrary.backend.expense.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ExpenseRequest(
    @NotBlank @Size(max = 100) String title,
    @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
    @NotBlank String date,
    String description
) {}
