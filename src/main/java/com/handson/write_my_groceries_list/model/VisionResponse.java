package com.handson.write_my_groceries_list.model;

import java.util.List;

public class VisionResponse {

    private String id;

    private String object;

    private long created;

    private String model;

    private List<Choice> choices;

    private Usage usage;

    private String system_fingerprint;


    public VisionResponse() { }

    public VisionResponse(String id, String object, long created, String model, List<Choice> choices, Usage usage, String system_fingerprint) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
        this.usage = usage;
        this.system_fingerprint = system_fingerprint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public String getSystemFingerprint() {
        return system_fingerprint;
    }

    public void setSystemFingerprint(String system_fingerprint) {
        this.system_fingerprint = system_fingerprint;
    }


    public static class Choice {

        private int index;

        private Message message;

        private float logprobs;

        private String finish_reason;


        public Choice() { }

        public Choice(int index, Message message, float logprobs, String finish_reason) {
            this.index = index;
            this.message = message;
            this.logprobs = logprobs;
            this.finish_reason = finish_reason;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public float getLogprobs() {
            return logprobs;
        }

        public void setLogprobs(float logprobs) {
            this.logprobs = logprobs;
        }

        public String getFinishReason() {
            return finish_reason;
        }

        public void setFinishReason(String finish_reason) {
            this.finish_reason = finish_reason;
        }


        public static class Message {

            private String role;

            private String content;

            private Object refusal; // unknown type


            public Message() { }

            public Message(String role, String content, Object refusal) {
                this.role = role;
                this.content = content;
                this.refusal = refusal;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Object getRefusal() {
                return refusal;
            }

            public void setRefusal(Object refusal) {
                this.refusal = refusal;
            }
        }
    }


    public static class Usage {

        private int prompt_tokens;

        private int completion_tokens;

        private int total_tokens;


        public Usage() { }

        public Usage(int prompt_tokens, int completion_tokens, int total_tokens) {
            this.prompt_tokens = prompt_tokens;
            this.completion_tokens = completion_tokens;
            this.total_tokens = total_tokens;
        }

        public int getPromptTokens() {
            return prompt_tokens;
        }

        public void setPromptTokens(int prompt_tokens) {
            this.prompt_tokens = prompt_tokens;
        }

        public int getCompletionTokens() {
            return completion_tokens;
        }

        public void setCompletionTokens(int completion_tokens) {
            this.completion_tokens = completion_tokens;
        }

        public int getTotalTokens() {
            return total_tokens;
        }

        public void setTotalTokens(int total_tokens) {
            this.total_tokens = total_tokens;
        }

    }

}
