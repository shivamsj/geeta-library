package com.geetalibrary.backend.expense.dto;

import java.math.BigDecimal;

public record ExpenseResponse(String id, String title, BigDecimal amount, String date, String description, long updatedAt) {}
