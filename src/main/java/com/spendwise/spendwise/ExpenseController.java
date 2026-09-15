package com.spendwise.spendwise;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;


    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;

    }
    @GetMapping
    public Page<Expense> getAllExpenses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            Pageable pageable) {
        return expenseService.getExpenses(category, from, to, pageable);
    }
    //create
    @PostMapping
    public Expense addExpense(@Valid @RequestBody ExpenseRequest request){
        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setNote(request.getNote());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate()!=null ? request.getDate():java.time.LocalDate.now());
        return expenseService.addExpense(expense);
    }

    //read
    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id){
        return expenseService.getExpenseById(id);
    }

    //update
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseRequest request) {
        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setNote(request.getNote());
        expense.setCategory(request.getCategory());
        return expenseService.updateExpense(id, expense);
    }
    
     //delete
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/summary/total")
    public Double getMonthlyTotal(@RequestParam int year, @RequestParam int month) {
        return expenseService.getMonthlyTotal(year, month);
    }

    @GetMapping("/summary/by-category")
    public Map<String, Double> getCategoryBreakdown(@RequestParam int year, @RequestParam int month) {
        return expenseService.getMonthlyCategoryBreakdown(year, month);
    }
}