package com.geetalibrary.backend.member.dto;

import java.math.BigDecimal;

public record MemberResponse(
    String id,
    String name,
    String mobile,
    String address,
    String seatNumber,
    String planName,
    String joiningDate,
    String expiryDate,
    BigDecimal dueAmount,
    String status,
    long updatedAt
) {}
