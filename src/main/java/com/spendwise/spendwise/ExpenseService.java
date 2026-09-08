package com.spendwise.spendwise;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public List<Expense> getExpenses(String category, LocalDate from, LocalDate to) {
        return expenseRepository.findWithFilters(getCurrentUser(), category, from, to);
    }

    // Helper: who is making this request?
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public Expense addExpense(Expense expense){
        expense.setUser(getCurrentUser());       // stamp it with the owner
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findByUser(getCurrentUser());   // only my expenses
    }

    public Expense getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense == null || !expense.getUser().getId().equals(getCurrentUser().getId())) {
            return null;    // not found, or not yours
        }
        return expense;
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
        Expense existing = getExpenseById(id);   // reuses the ownership check
        if (existing == null) {
            return null;
        }
        existing.setAmount(updatedExpense.getAmount());
        existing.setNote(updatedExpense.getNote());
        existing.setCategory(updatedExpense.getCategory());
        return expenseRepository.save(existing);
    }

    public void deleteExpense(Long id) {
        Expense existing = getExpenseById(id);   // ownership check
        if (existing != null) {
            expenseRepository.deleteById(id);
        }
    }
    public Double getMonthlyTotal(int year, int month) {
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to = from.withDayOfMonth(from.lengthOfMonth());
        return expenseRepository.getTotalForPeriod(getCurrentUser(), from, to);
    }

    public Map<String, Double> getMonthlyCategoryBreakdown(int year, int month) {
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to = from.withDayOfMonth(from.lengthOfMonth());

        List<Object[]> rows = expenseRepository.getCategoryBreakdown(getCurrentUser(), from, to);

        Map<String, Double> breakdown = new HashMap<>();
        for (Object[] row : rows) {
            String category = (String) row[0];
            Double total = (Double) row[1];
            breakdown.put(category, total);
        }
        return breakdown;
    }
}