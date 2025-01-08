package com.ptit.EnglishExplorer.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/gemini")
public class GeminiController {

    private final RestTemplate restTemplate = new RestTemplate();
    @PostMapping("/generate-content")
    public ResponseEntity<?> generateContent(@RequestBody Map<String, String> request) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyAyag8B-oqlqQQfMk-sksNMZ4nILO4vEcQ";

        // Lấy message từ request
        String userMessage = request.get("message");

        // Xây dựng prompt cho ngữ cảnh chuyên gia
        String prompt = "Bạn là một chuyên gia giảng dạy tiếng Anh với nhiều năm kinh nghiệm. Hãy trả lời câu hỏi sau bằng cách cung cấp thông tin chi tiết và dễ hiểu: \"" 
                        + userMessage + "\"";

        // Tạo body với prompt
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        Map<String, Object> part = new HashMap<>();
        part.put("text", prompt);
        content.put("parts", new Map[]{part});
        requestBody.put("contents", new Map[]{content});

        // Tạo headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Tạo HttpEntity chứa body và headers
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Gửi POST request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
