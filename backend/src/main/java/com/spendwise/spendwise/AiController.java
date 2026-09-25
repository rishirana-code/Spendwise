package com.spendwise.spendwise;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final ExpenseService expenseService;

    public AiController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/insights")
    public String getInsights(@RequestParam int year, @RequestParam int month) {
        return expenseService.getMonthlyInsights(year, month);
    }


}
