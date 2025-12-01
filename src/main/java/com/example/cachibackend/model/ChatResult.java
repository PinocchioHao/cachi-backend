package com.example.cachibackend.model;

public class ChatResult {

    private String content;  // LLM 输出文本
    private String model;    // 可选，返回使用的模型名

    public ChatResult() {}

    public ChatResult(String content, String model) {
        this.content = content;
        this.model = model;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
