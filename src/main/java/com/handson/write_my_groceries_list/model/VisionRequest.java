package com.handson.write_my_groceries_list.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VisionRequest {

    private String model;

    private List<Message> messages;

    @JsonProperty("max_tokens")
    private int maxTokens;

    public VisionRequest() { }

    public VisionRequest(String model, List<Message> messages, int maxTokens) {
        this.model = model;
        this.messages = messages;
        this.maxTokens = maxTokens;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public static class Message {

        private String role;

        private List<Object> content;  // We'll use Object type to allow both TextContent and ImageContent

        public Message() { }

        public Message(String role, List<Object> content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public List<Object> getContent() {
            return content;
        }

        public void setContent(List<Object> content) {
            this.content = content;
        }
    }

    // Class specifically for handling text content
    public static class TextContent {

        private String type;
        private String text;

        public TextContent() { }

        public TextContent(String type, String text) {
            this.type = type;
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    // Class specifically for handling image_url content
    public static class ImageContent {

        private String type;

        @JsonProperty("image_url")
        private ImageUrl imageUrl;

        public ImageContent() { }

        public ImageContent(String type, ImageUrl imageUrl) {
            this.type = type;
            this.imageUrl = imageUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ImageUrl getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(ImageUrl imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class ImageUrl {

        private String url;

        public ImageUrl() { }

        public ImageUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}










//package com.handson.write_my_groceries_list.model;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import java.util.List;
//
//public class VisionRequest {
//
//    private String model;
//
//    private List<Message> messages;
//
//    @JsonProperty("max_tokens")
//    private int maxTokens;
//
//
//    public VisionRequest() { }
//
//    public VisionRequest(String model, List<Message> messages, int maxTokens) {
//        this.model = model;
//        this.messages = messages;
//        this.maxTokens = maxTokens;
//    }
//
//    public String getModel() {
//        return model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }
//
//    public List<Message> getMessages() {
//        return messages;
//    }
//
//    public void setMessages(List<Message> messages) {
//        this.messages = messages;
//    }
//
//    public int getMaxTokens() {
//        return maxTokens;
//    }
//
//    public void setMaxTokens(int maxTokens) {
//        this.maxTokens = maxTokens;
//    }
//
//
//    public static class Message {
//
//        private String role;
//
//        private List<Content> content;
//
//
//        public Message() { }
//
//        public Message(String role, List<Content> content) {
//            this.role = role;
//            this.content = content;
//        }
//
//        public String getRole() {
//            return role;
//        }
//
//        public void setRole(String role) {
//            this.role = role;
//        }
//
//        public List<Content> getContent() {
//            return content;
//        }
//
//        public void setContent(List<Content> content) {
//            this.content = content;
//        }
//    }
//
//
//    public static class Content {
//
//        private String type;
//
//        private String text;
//
//        @JsonProperty("image_url")
//        private ImageUrl imageUrl;
//
//
//        public Content() { }
//
//        public Content(String type, String text, ImageUrl imageUrl) {
//            this.type = type;
//            this.text = text;
//            this.imageUrl = imageUrl;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public void setText(String text) {
//            this.text = text;
//        }
//
//        public ImageUrl getImageUrl() {
//            return imageUrl;
//        }
//
//        public void setImageUrl(ImageUrl imageUrl) {
//            this.imageUrl = imageUrl;
//        }
//    }
//
//
//    public static class ImageUrl {
//
//        private String url;
//
//
//        public ImageUrl() { }
//
//        public ImageUrl(String url) {
//            this.url = url;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//    }
//
//}







//package com.handson.write_my_groceries_list.model;
//
//import java.util.List;
//
//public class VisionRequest {
//
//    private String model;
//
//    private List<Message> messages;
//
//    private int max_tokens;
//
//
//    public VisionRequest() { }
//
//    public VisionRequest(String model, List<Message> messages, int max_tokens) {
//        this.model = model;
//        this.messages = messages;
//        this.max_tokens = max_tokens;
//    }
//
//    public String getModel() {
//        return model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }
//
//    public List<Message> getMessages() {
//        return messages;
//    }
//
//    public void setMessages(List<Message> messages) {
//        this.messages = messages;
//    }
//
//    public int getMaxTokens() {
//        return max_tokens;
//    }
//
//    public void setMaxTokens(int max_tokens) {
//        this.max_tokens = max_tokens;
//    }
//
//    public static class Message {
//
//        private String role;
//
//        private List<Content> content;
//
//
//        public Message() { }
//
//        public Message(String role, List<Content> content) {
//            this.role = role;
//            this.content = content;
//        }
//
//        public String getRole() {
//            return role;
//        }
//
//        public void setRole(String role) {
//            this.role = role;
//        }
//
//        public List<Content> getContent() {
//            return content;
//        }
//
//        public void setContent(List<Content> content) {
//            this.content = content;
//        }
//    }
//
//
//    public static class Content {
//
//        private String type;
//
//        private String text;
//
//        private String image_url;
//
//
//        public Content(){ }
//
//        public Content(String type, String text, String image_url) {
//            this.type = type;
//            this.text = text;
//            this.image_url = image_url;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public void setText(String text) {
//            this.text = text;
//        }
//
//        public String getImageUrl() {
//            return image_url;
//        }
//
//        public void setImageUrl(String image_url) {
//            this.image_url = image_url;
//        }
//    }
//
//}



//package com.handson.write_my_groceries_list.model;
//
//import java.util.List;
//
//public class VisionRequest {
//
//    private String model;
//
//    private List<Message> messages;
//
//    private int max_tokens;
//
//
//    public VisionRequest() { }
//
//    public VisionRequest(String model, List<Message> messages, int max_tokens) {
//        this.model = model;
//        this.messages = messages;
//        this.max_tokens = max_tokens;
//    }
//
//    public String getModel() {
//        return model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }
//
//    public List<Message> getMessages() {
//        return messages;
//    }
//
//    public void setMessages(List<Message> messages) {
//        this.messages = messages;
//    }
//
//    public int getMaxTokens() {
//        return max_tokens;
//    }
//
//    public void setMaxTokens(int max_tokens) {
//        this.max_tokens = max_tokens;
//    }
//
//    public static class Message {
//
//        private String role;
//
//        private List<Content> content;
//
//
//        public Message() { }
//
//        public Message(String role, List<Content> content) {
//            this.role = role;
//            this.content = content;
//        }
//
//        public String getRole() {
//            return role;
//        }
//
//        public void setRole(String role) {
//            this.role = role;
//        }
//
//        public List<Content> getContent() {
//            return content;
//        }
//
//        public void setContent(List<Content> content) {
//            this.content = content;
//        }
//    }
//
//
//    public static class Content {
//
//        private String type;
//
//        private String text;
//
//        private ImageUrl image_url;
//
//
//        public Content(){ }
//
//        public Content(String type, String text, ImageUrl image_url) {
//            this.type = type;
//            this.text = text;
//            this.image_url = image_url;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public void setText(String text) {
//            this.text = text;
//        }
//
//        public ImageUrl getImageUrl() {
//            return image_url;
//        }
//
//        public void setImageUrl(ImageUrl image_url) {
//            this.image_url = image_url;
//        }
//    }
//
//
//    public static class ImageUrl {
//
//        private String url;
//
//
//        public ImageUrl() { }
//
//        public ImageUrl(String url) {
//            this.url = url;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//    }
//}
