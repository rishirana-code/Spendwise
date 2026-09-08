package com.spendwise.spendwise;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;


    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;

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

    @GetMapping
    public List<Expense> getAllExpenses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate from,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate to) {
        return expenseService.getExpenses(category, from, to);
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