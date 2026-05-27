package com.example.cachibackend.model;

public class ChatResult {

    private String content;  // LLM input content
    private String model;    // model name - optional

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
