package com.geetalibrary.backend.member;

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
@Table(name = "members")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 20)
    private String mobile;
    @Column(columnDefinition = "TEXT")
    private String address;
    @Column(length = 20)
    private String seatNumber;
    @Column(length = 100)
    private String planName;
    private LocalDate joiningDate;
    private LocalDate expiryDate;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal dueAmount = BigDecimal.ZERO;
    @Column(nullable = false, length = 20)
    private String status = "ACTIVE";
    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    protected Member() {}
    public Member(User owner) { this.owner = owner; }

    public Long getId() { return id; }
    public User getOwner() { return owner; }
    public String getName() { return name; }
    public String getMobile() { return mobile; }
    public String getAddress() { return address; }
    public String getSeatNumber() { return seatNumber; }
    public String getPlanName() { return planName; }
    public LocalDate getJoiningDate() { return joiningDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public BigDecimal getDueAmount() { return dueAmount; }
    public String getStatus() { return status; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void update(String name, String mobile, String address, String seatNumber, String planName,
                       LocalDate joiningDate, LocalDate expiryDate, BigDecimal dueAmount, String status) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.seatNumber = seatNumber;
        this.planName = planName;
        this.joiningDate = joiningDate;
        this.expiryDate = expiryDate;
        this.dueAmount = dueAmount == null ? BigDecimal.ZERO : dueAmount;
        this.status = status == null || status.isBlank() ? "ACTIVE" : status.toUpperCase();
        this.updatedAt = Instant.now();
    }
}
