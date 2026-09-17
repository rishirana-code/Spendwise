package com.spendwise.spendwise;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {
    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.6-flash:generateContent";
       public String ask(String prompt){
           // Build the request body in the shape Gemini expects
           Map<String, Object> requestBody = Map.of(
                   "contents", List.of(
                           Map.of("parts", List.of(
                                   Map.of("text", prompt)
                           ))
                   )
           );

           // Make the POST call
           Map<String, Object> response = restClient.post()
                   .uri(GEMINI_URL + "?key=" + apiKey)
                   .header("Content-Type", "application/json")
                   .body(requestBody)
                   .retrieve()
                   .body(Map.class);

           return extractText(response);
       }

    @SuppressWarnings("unchecked")
    private String extractText(Map<String, Object> response) {
        try {
            List<Map<String, Object>> candidates =
                    (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> content =
                    (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts =
                    (List<Map<String, Object>>) content.get("parts");
            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            return "Could not parse AI response.";
        }
    }
    public String categorize(String note) {
           String prompt = "You are an expense categorizer. Categorize the following expense "
                   + "into EXACTLY ONE of these categories: Food, Travel, Shopping, Bills, "
                   + "Entertainment, Health, Other. "
                   + "Reply with ONLY the single category word and nothing else. "
                   + "Expense: \"" + note + "\"";

           String response = ask(prompt);

           return cleanCategory(response);
    }
    private String cleanCategory(String raw){
        if (raw == null) return "Other";
        String cleaned = raw.trim().replaceAll("[^a-zA-Z]", ""); // strip punctuation/whitespace

        List<String> allowed = List.of(
                "Food", "Travel", "Shopping", "Bills", "Entertainment", "Health", "Other");

        for (String category : allowed) {
            if (category.equalsIgnoreCase(cleaned)) {
                return category;   // return in proper casing
            }
        }
        return "Other";
    }
    public String generateInsights(String month,Double total,Map<String,Double>breakdown){
        StringBuilder breakdownText = new StringBuilder();
        for (Map.Entry<String, Double> entry : breakdown.entrySet()) {
            breakdownText.append("- ").append(entry.getKey())
                    .append(": ").append(entry.getValue()).append("\n");
        }
        String prompt = "You are a friendly personal finance advisor. "
                + "Analyze this user's spending for " + month + ".\n\n"
                + "Total spent: " + total + "\n"
                + "Breakdown by category:\n" + breakdownText + "\n"
                + "Give a short, friendly analysis in 3-4 sentences. "
                + "Point out the biggest spending area, whether the balance looks healthy, "
                + "and 1-2 specific, actionable suggestions to save money. "
                + "Be encouraging, not judgmental. Do not use markdown formatting.";

        return ask(prompt);
    }
}
