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
}
