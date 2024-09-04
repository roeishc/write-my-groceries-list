package com.handson.write_my_groceries_list.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handson.write_my_groceries_list.model.ChatCompletionResponse;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public class GptService {

    private static final Logger logger = LoggerFactory.getLogger(GptService.class);

    @Value("${openai.apikey}")
    private String openaiApiKey;

    @Autowired
    private ObjectMapper om;


    public String testingImage(String textPrompt, String imageUrl) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create("{\r\n    \"model\": \"gpt-4o-mini\",\r\n    \"messages\": [\r\n      {\r\n        \"role\": \"user\",\r\n        \"content\": [\r\n          {\r\n            \"type\": \"text\",\r\n            \"text\": \"What’s in this image?\"\r\n          },\r\n          {\r\n            \"type\": \"image_url\",\r\n            \"image_url\": {\r\n              \"url\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Gfp-wisconsin-madison-the-nature-boardwalk.jpg/2560px-Gfp-wisconsin-madison-the-nature-boardwalk.jpg\"\r\n            }\r\n          }\r\n        ]\r\n      }\r\n    ],\r\n    \"max_tokens\": 300\r\n  }", mediaType);
        RequestBody body = RequestBody.create("{\r\n    \"model\": \"gpt-4o-mini\",\r\n    \"messages\": [\r\n      {\r\n        \"role\": \"user\",\r\n        \"content\": [\r\n          {\r\n            \"type\": \"text\",\r\n            \"text\": " + textPrompt + "\r\n          },\r\n          {\r\n            \"type\": \"image_url\",\r\n            \"image_url\": {\r\n              \"url\": " + imageUrl + "\r\n            }\r\n          }\r\n        ]\r\n      }\r\n    ],\r\n    \"max_tokens\": 300\r\n  }", mediaType);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + openaiApiKey)
                .build();
        String res;
        try (Response response = client.newCall(request).execute()) {
            if (response == null || response.body() == null){
                logger.warn("GPT POST request failed, empty response or response body.");
                return "GPT POST request failed";
            }
            System.out.println(response.body());
            res = response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public String testingText(String textPrompt) {
        textPrompt = "\"" + textPrompt + "\"";
        String systemRoleContent = "\"You are a helpful assistant.\"";

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"model\": \"gpt-4o-mini\",\r\n    \"messages\": [\r\n      {\r\n        \"role\": \"system\",\r\n        \"content\": " + systemRoleContent + "\r\n      },\r\n      {\r\n        \"role\": \"user\",\r\n        \"content\": " + textPrompt + "\r\n      }\r\n    ]\r\n  }");
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + openaiApiKey)
                .build();

        ChatCompletionResponse chatCompletionResponse;
        try (Response response = client.newCall(request).execute()) {
            if (response == null || response.body() == null){
                logger.warn("GPT POST request failed, empty response or response body.");
                return "GPT POST request failed";
            }
            chatCompletionResponse = om.readValue(response.body().string(), ChatCompletionResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return chatCompletionResponse.getChoices().get(0).getMessage().getContent();
    }

}
