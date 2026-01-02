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
    private final ObjectMapper mapper;
    private final OpenRouterConfig config;

    public OpenRouterService(OpenRouterConfig config) {
        this.config = config;
        this.mapper = new ObjectMapper()
                .configure(
                        com.fasterxml.jackson.databind.DeserializationFeature
                                .FAIL_ON_UNKNOWN_PROPERTIES,
                        false
                );
    }

    public ChatResult chatSimplified(String userMessage, String model) throws Exception {

        OpenRouterRequest request = new OpenRouterRequest();
        request.setModel(model);
        request.setMessages(
                List.of(new OpenRouterRequest.Message("user", userMessage))
        );

        String json = mapper.writeValueAsString(request);
        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json")
        );

        Request httpRequest = new Request.Builder()
                .url("https://openrouter.ai/api/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + config.getApiKey())
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(httpRequest).execute()) {

            if (!response.isSuccessful()) {
                throw new RuntimeException(
                        "OpenRouter 调用失败，HTTP=" + response.code()
                );
            }

            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new RuntimeException("OpenRouter 返回 body 为空");
            }

            String respJson = responseBody.string();
            OpenRouterResponse fullResp =
                    mapper.readValue(respJson, OpenRouterResponse.class);

            String content = extractContentSafely(fullResp);

            return new ChatResult(content, fullResp.getModel());
        }
    }

    /**
     * 专门负责从 OpenRouterResponse 中安全提取内容
     */
    private String extractContentSafely(OpenRouterResponse resp) {
        if (resp == null || resp.getChoices() == null || resp.getChoices().isEmpty()) {
            return "（AI 未返回有效内容）";
        }

        OpenRouterResponse.Choice choice = resp.getChoices().get(0);
        if (choice.getMessage() == null) {
            return "（AI 消息为空）";
        }

        String content = choice.getMessage().getContent();
        return content != null ? content.trim() : "（AI 内容为空）";
    }
}
