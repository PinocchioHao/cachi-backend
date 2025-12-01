package com.example.cachibackend.service;

import com.example.cachibackend.config.OpenRouterConfig;
import com.example.cachibackend.model.ChatResult;
import com.example.cachibackend.model.OpenRouterRequest;
import com.example.cachibackend.model.OpenRouterResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OpenRouterService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final OpenRouterConfig config;

    public OpenRouterService(OpenRouterConfig config) {
        this.config = config;
    }

    public ChatResult chatSimplified(String userMessage, String model) throws Exception {
        OpenRouterRequest request = new OpenRouterRequest();
        request.setModel(model);
        request.setMessages(List.of(new OpenRouterRequest.Message("user", userMessage)));

        String json = mapper.writeValueAsString(request);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        try (Response response = client.newCall(
                new Request.Builder()
                        .url("https://openrouter.ai/api/v1/chat/completions")
                        .addHeader("Authorization", "Bearer " + config.getApiKey())
                        .addHeader("Content-Type", "application/json")
                        .post(body)
                        .build()
        ).execute()) {

            if (!response.isSuccessful()) {
                throw new RuntimeException("OpenRouter调用失败：" + response);
            }

            String respJson = response.body().string();
            OpenRouterResponse fullResp = mapper.readValue(respJson, OpenRouterResponse.class);

            String content = "";
            if (fullResp.getChoices() != null && !fullResp.getChoices().isEmpty()) {
                OpenRouterResponse.Choice choice = fullResp.getChoices().get(0);
                OpenRouterResponse.Choice.ChoiceMessage msg = choice.getMessage();
                if (msg != null && msg.getContent() != null) {
                    content = msg.getContent().trim();
                }
            }

            return new ChatResult(content, fullResp.getModel());
        }
    }
}
