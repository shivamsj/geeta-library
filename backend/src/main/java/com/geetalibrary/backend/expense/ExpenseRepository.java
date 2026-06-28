package com.geetalibrary.backend.expense;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByOwnerIdOrderByExpenseDateDesc(Long ownerId);
    Optional<Expense> findByIdAndOwnerId(Long id, Long ownerId);
}
