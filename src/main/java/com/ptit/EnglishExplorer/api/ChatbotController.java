package com.ptit.EnglishExplorer.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chatbot")
public class ChatbotController {

    @PostMapping("/message")
    public ResponseEntity<String> getBotResponse(@RequestBody Map<String, String> request) {
        String userInput = request.get("message");
        String botResponse = getBotResponseFromPython(userInput);
        return ResponseEntity.ok(botResponse);
    }

    private String getBotResponseFromPython(String userInput) {
        // Chuyển userInput thành chữ thường
        String lowerCaseInput = userInput.toLowerCase();

        // Tạo kết nối tới Flask API (Python chatbot)
        try {
            URL url = new URL("http://localhost:5000/api/chat");  // Flask app đang chạy ở cổng 5000
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonInputString = "{\"user_input\": \"" + lowerCaseInput + "\"}";

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
