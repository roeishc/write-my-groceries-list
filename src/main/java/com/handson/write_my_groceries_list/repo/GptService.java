package com.handson.write_my_groceries_list.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handson.write_my_groceries_list.model.ChatCompletionResponse;
import com.handson.write_my_groceries_list.model.VisionRequest;
import com.handson.write_my_groceries_list.model.VisionResponse;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class GptService {

    private static final Logger logger = LoggerFactory.getLogger(GptService.class);

    @Value("${openai.apikey}")
    private String openaiApiKey;

    @Autowired
    private ObjectMapper om;


    public String getDescriptionOfReceiptImage(String imageUrl) {

        VisionRequest.Message message = getMessage(imageUrl);

        // create the VisionRequest object
        VisionRequest visionRequest = new VisionRequest();
        visionRequest.setModel("gpt-4o-mini");
        visionRequest.setMessages(List.of(message));
        visionRequest.setMaxTokens(300);

        // serialize VisionRequest to JSON
        String requestBodyString;
        try {
            requestBodyString = om.writeValueAsString(visionRequest);
        } catch (IOException e) {
            logger.error("Error serializing VisionRequest", e);
            return "Error creating request body";
        }

        // send the request
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBodyString);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + openaiApiKey)
                .build();

        // parse response
        VisionResponse visionResponse;
        try (Response response = client.newCall(request).execute()) {
            if (response == null || response.body() == null) {
                logger.warn("Empty Vision response");
                return "Vision request failed";
            }
            visionResponse = om.readValue(response.body().string(), VisionResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return visionResponse.getChoices().get(0).getMessage().getContent();
    }

    @NotNull
    private static VisionRequest.Message getMessage(String imageUrl) {
        String textPrompt = "In this image there's a receipt in English. Please list all items in this receipt.";

        // create the TextContent and ImageContent objects
        VisionRequest.TextContent textContent = new VisionRequest.TextContent("text", textPrompt);

        VisionRequest.ImageUrl imgUrl = new VisionRequest.ImageUrl(imageUrl);
        VisionRequest.ImageContent imageContent = new VisionRequest.ImageContent("image_url", imgUrl);

        // create the Message object containing both text and image contents
        VisionRequest.Message message = new VisionRequest.Message();
        message.setRole("user");
        message.setContent(Arrays.asList(textContent, imageContent));
        return message;
    }


    public String getResponseForText(String textPrompt) {
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
