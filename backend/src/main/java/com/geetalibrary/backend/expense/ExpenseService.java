package com.geetalibrary.backend.expense;

import com.geetalibrary.backend.common.ResourceNotFoundException;
import com.geetalibrary.backend.expense.dto.ExpenseRequest;
import com.geetalibrary.backend.expense.dto.ExpenseResponse;
import com.geetalibrary.backend.user.User;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenses;
    public ExpenseService(ExpenseRepository expenses) { this.expenses = expenses; }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> findAll(User owner) {
        return expenses.findAllByOwnerIdOrderByExpenseDateDesc(owner.getId()).stream().map(this::response).toList();
    }

    @Transactional
    public ExpenseResponse create(User owner, ExpenseRequest request) {
        Expense expense = new Expense(owner);
        apply(expense, request);
        return response(expenses.save(expense));
    }

    @Transactional
    public ExpenseResponse update(User owner, Long id, ExpenseRequest request) {
        Expense expense = owned(owner, id);
        apply(expense, request);
        return response(expense);
    }

    @Transactional public void delete(User owner, Long id) { expenses.delete(owned(owner, id)); }

    private Expense owned(User owner, Long id) {
        return expenses.findByIdAndOwnerId(id, owner.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
    }

    private void apply(Expense expense, ExpenseRequest request) {
        try {
            expense.update(request.title().trim(), request.amount(), LocalDate.parse(request.date()),
                request.description() == null ? "" : request.description().trim());
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Expense date must use YYYY-MM-DD format");
        }
    }

    private ExpenseResponse response(Expense expense) {
        return new ExpenseResponse(expense.getId().toString(), expense.getTitle(), expense.getAmount(),
            expense.getExpenseDate().toString(), expense.getDescription(), expense.getUpdatedAt().toEpochMilli());
    }
}
