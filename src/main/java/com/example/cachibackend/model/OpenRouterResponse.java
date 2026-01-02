package com.example.cachibackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenRouterResponse {

    private String id;
    private String provider;
    private String model;
    private String object;
    private long created;
    private List<Choice> choices;
    private Usage usage;

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public long getCreated() { return created; }
    public void setCreated(long created) { this.created = created; }

    public List<Choice> getChoices() { return choices; }
    public void setChoices(List<Choice> choices) { this.choices = choices; }

    public Usage getUsage() { return usage; }
    public void setUsage(Usage usage) { this.usage = usage; }

    // ======================
    // Inner Classes
    // ======================

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        private int index;
        private String finish_reason;
        private String native_finish_reason;
        private ChoiceMessage message;
        private Object logprobs;

        public int getIndex() { return index; }
        public void setIndex(int index) { this.index = index; }

        public String getFinish_reason() { return finish_reason; }
        public void setFinish_reason(String finish_reason) { this.finish_reason = finish_reason; }

        public String getNative_finish_reason() { return native_finish_reason; }
        public void setNative_finish_reason(String native_finish_reason) {
            this.native_finish_reason = native_finish_reason;
        }

        public ChoiceMessage getMessage() { return message; }
        public void setMessage(ChoiceMessage message) { this.message = message; }

        public Object getLogprobs() { return logprobs; }
        public void setLogprobs(Object logprobs) { this.logprobs = logprobs; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChoiceMessage {
        private String role;
        private String content;
        private Object refusal;
        private String reasoning;
        private List<ReasoningDetail> reasoning_details;

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public Object getRefusal() { return refusal; }
        public void setRefusal(Object refusal) { this.refusal = refusal; }

        public String getReasoning() { return reasoning; }
        public void setReasoning(String reasoning) { this.reasoning = reasoning; }

        public List<ReasoningDetail> getReasoning_details() { return reasoning_details; }
        public void setReasoning_details(List<ReasoningDetail> reasoning_details) {
            this.reasoning_details = reasoning_details;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReasoningDetail {
        private String format;
        private int index;
        private String type;
        private String text;

        public String getFormat() { return format; }
        public void setFormat(String format) { this.format = format; }

        public int getIndex() { return index; }
        public void setIndex(int index) { this.index = index; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
        private PromptTokensDetails prompt_tokens_details;
        private CompletionTokensDetails completion_tokens_details;

        public int getPrompt_tokens() { return prompt_tokens; }
        public void setPrompt_tokens(int prompt_tokens) { this.prompt_tokens = prompt_tokens; }

        public int getCompletion_tokens() { return completion_tokens; }
        public void setCompletion_tokens(int completion_tokens) {
            this.completion_tokens = completion_tokens;
        }

        public int getTotal_tokens() { return total_tokens; }
        public void setTotal_tokens(int total_tokens) { this.total_tokens = total_tokens; }

        public PromptTokensDetails getPrompt_tokens_details() {
            return prompt_tokens_details;
        }
        public void setPrompt_tokens_details(PromptTokensDetails prompt_tokens_details) {
            this.prompt_tokens_details = prompt_tokens_details;
        }

        public CompletionTokensDetails getCompletion_tokens_details() {
            return completion_tokens_details;
        }
        public void setCompletion_tokens_details(
                CompletionTokensDetails completion_tokens_details) {
            this.completion_tokens_details = completion_tokens_details;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PromptTokensDetails {
        private int cached_tokens;
        private int audio_tokens;
        private int video_tokens;

        public int getCached_tokens() { return cached_tokens; }
        public void setCached_tokens(int cached_tokens) {
            this.cached_tokens = cached_tokens;
        }

        public int getAudio_tokens() { return audio_tokens; }
        public void setAudio_tokens(int audio_tokens) {
            this.audio_tokens = audio_tokens;
        }

        public int getVideo_tokens() { return video_tokens; }
        public void setVideo_tokens(int video_tokens) {
            this.video_tokens = video_tokens;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CompletionTokensDetails {
        private int reasoning_tokens;
        private int image_tokens;

        public int getReasoning_tokens() { return reasoning_tokens; }
        public void setReasoning_tokens(int reasoning_tokens) {
            this.reasoning_tokens = reasoning_tokens;
        }

        public int getImage_tokens() { return image_tokens; }
        public void setImage_tokens(int image_tokens) {
            this.image_tokens = image_tokens;
        }
    }
}
