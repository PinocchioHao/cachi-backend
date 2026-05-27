package com.example.cachibackend.controller;

import com.example.cachibackend.model.ApiResponse;
import com.example.cachibackend.model.ChatResult;
import com.example.cachibackend.model.OpenRouterRequest;
import com.example.cachibackend.service.OpenRouterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

    private final OpenRouterService service;

    public ChatController(OpenRouterService service) {
        this.service = service;
    }

    private static final String DEFAULT_MODEL = "openai/gpt-oss-120b:free";

    @PostMapping
    public ApiResponse<ChatResult> chat(@RequestBody OpenRouterRequest request) {
        try {
            if (request.getMessages() == null || request.getMessages().isEmpty()) {
                return ApiResponse.error(400, "messages cannot be empty");
            }

            String userMessage = request.getMessages().get(0).getContent();
            String model = request.getModel() != null ? request.getModel() : DEFAULT_MODEL;

            ChatResult result = service.chatSimplified(userMessage, model);
            return ApiResponse.success(result);

        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }
}
