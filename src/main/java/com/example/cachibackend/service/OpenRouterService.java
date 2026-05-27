package com.example.cachibackend.service;

import com.example.cachibackend.config.OpenRouterConfig;
import com.example.cachibackend.model.ChatResult;
import com.example.cachibackend.model.OpenRouterRequest;
import com.example.cachibackend.model.OpenRouterResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OpenRouterService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper;
    private final OpenRouterConfig config;
    private static final Logger logger = LoggerFactory.getLogger(OpenRouterService.class);

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

        try {
            // 构建请求对象
            OpenRouterRequest request = new OpenRouterRequest();
            request.setModel(model);
            request.setMessages(
                    List.of(new OpenRouterRequest.Message("user", userMessage))
            );

            String json = mapper.writeValueAsString(request);
            logger.debug("OpenRouter 请求 JSON: {}", json);
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
                    logger.error("OpenRouter 调用失败，HTTP={}，请求model={}", response.code(), model);
                    throw new RuntimeException(
                            "OpenRouter 调用失败，HTTP=" + response.code()
                    );
                }

                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    logger.error("OpenRouter 返回 body 为空，requestModel={}", model);
                    throw new RuntimeException("OpenRouter 返回 body 为空");
                }

                String respJson = responseBody.string();
                logger.debug("OpenRouter 返回 JSON: {}", respJson);
                OpenRouterResponse fullResp =
                        mapper.readValue(respJson, OpenRouterResponse.class);

                String content = extractContentSafely(fullResp);

                return new ChatResult(content, fullResp.getModel());
            }
        } catch (Exception e) {
            // 在所有未预见的异常处打印错误日志并向上抛出，保留原有异常语义
            logger.error("chatSimplified 发生异常，userMessage={}, model={}", userMessage, model, e);
            throw e;
        }
    }

    /**
     * 专门负责从 OpenRouterResponse 中安全提取内容
     */
    private String extractContentSafely(OpenRouterResponse resp) {
        if (resp == null || resp.getChoices() == null || resp.getChoices().isEmpty()) {
            logger.warn("OpenRouterResponse 无效或没有 choices");
            return "（AI 未返回有效内容）";
        }

        OpenRouterResponse.Choice choice = resp.getChoices().get(0);
        if (choice.getMessage() == null) {
            logger.warn("OpenRouterResponse choice 的 message 为空");
            return "（AI 消息为空）";
        }

        String content = choice.getMessage().getContent();
        if (content == null) {
            logger.warn("OpenRouterResponse message content 为空");
            return "（AI 内容为空）";
        }
        return content.trim();
    }
}
