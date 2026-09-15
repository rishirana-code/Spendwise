package com.spendwise.spendwise;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final GeminiService geminiService;

    public AiController(GeminiService geminiService){
        this.geminiService=geminiService;
    }

    @GetMapping("/test")
    public String test(@RequestParam String prompt){
        return geminiService.ask(prompt);
    }
}
