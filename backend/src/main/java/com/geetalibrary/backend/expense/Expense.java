package com.geetalibrary.backend.expense;

import com.geetalibrary.backend.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDate expenseDate;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    protected Expense() {}
    public Expense(User owner) { this.owner = owner; }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getExpenseDate() { return expenseDate; }
    public String getDescription() { return description; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void update(String title, BigDecimal amount, LocalDate date, String description) {
        this.title = title;
        this.amount = amount;
        this.expenseDate = date;
        this.description = description;
        this.updatedAt = Instant.now();
    }
}
