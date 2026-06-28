package com.geetalibrary.backend.expense;

import com.geetalibrary.backend.expense.dto.ExpenseRequest;
import com.geetalibrary.backend.expense.dto.ExpenseResponse;
import com.geetalibrary.backend.user.User;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService service;
    public ExpenseController(ExpenseService service) { this.service = service; }

    @GetMapping public List<ExpenseResponse> findAll(@AuthenticationPrincipal User user) { return service.findAll(user); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public ExpenseResponse create(@AuthenticationPrincipal User user, @Valid @RequestBody ExpenseRequest request) {
        return service.create(user, request);
    }
    @PutMapping("/{id}")
    public ExpenseResponse update(@AuthenticationPrincipal User user, @PathVariable Long id,
                                  @Valid @RequestBody ExpenseRequest request) { return service.update(user, id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal User user, @PathVariable Long id) { service.delete(user, id); }
}
