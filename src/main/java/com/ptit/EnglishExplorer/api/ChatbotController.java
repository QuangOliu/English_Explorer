package com.ptit.EnglishExplorer.api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    @PostMapping("/message")
    public ResponseEntity<String> getBotResponse(@RequestBody Map<String, String> request) {
        String userInput = request.get("message");
        String botResponse = getBotResponseFromPython(userInput);
        return ResponseEntity.ok(botResponse);
    }

    private String getBotResponseFromPython(String userInput) {
        // Tạo kết nối tới Flask API (Python chatbot)
        try {
            URL url = new URL("http://localhost:5000/api/chat");  // Flask app đang chạy ở cổng 5000
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonInputString = "{\"user_input\": \"" + userInput + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with Python chatbot";
        }
    }
}
