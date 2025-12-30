package com.meowmatch.meowmatch.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.dto.OllamaResponseObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;


@Service
public class OllamaService {
    @Value("${ollama.request.url}")
    private String ollamaRequestUrl;
    private final CatService catService;
    private final ConversationService conversationService;

    public OllamaService(CatService catService, ConversationService conversationService) {
        this.catService = catService;
        this.conversationService = conversationService;
    }

    public String askOllama(String prompt) {
        try {
            // Build JSON body
            JsonObject body = new JsonObject();
            body.addProperty("model", "llama3.1");
            body.addProperty("prompt", prompt);
            body.addProperty("stream", false);

            String requestBody = new Gson().toJson(body);
            // Build HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ollamaRequestUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Send request
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Return raw JSON response or parse it if you want
            System.out.println(response.body());
            return response.body();

        } catch (IOException | InterruptedException e) {
            return "Error calling Ollama: " + e.getMessage();
        }
    }

    public ChatMessage responseToAddedMessageToConversation(String conversationId,
                                                            ChatMessage messageWithTime) {
        String promptedCat = createPromptWithCat( messageWithTime);
        String ollamaResponse = askOllama(promptedCat);
        System.out.println(ollamaResponse);

        Gson gson = new Gson();
        OllamaResponseObject ollamaResponseObject = gson.fromJson(ollamaResponse, OllamaResponseObject.class);
        ChatMessage newChatMessageFromOllama = new ChatMessage(ollamaResponseObject.response(), messageWithTime.targetedProfile(), messageWithTime.authorId(),  LocalDateTime.now());
        System.out.println(newChatMessageFromOllama);
//        System.out.println(ollamaResponseObject);
        return newChatMessageFromOllama;

    }
// todo: make the matched user generic
    private String createPromptWithCat( ChatMessage chatMessage) {
        Cat profileForPrompt=catService.findById(chatMessage.targetedProfile());
        return
                """
                        You are a %d year old %s %s . your name is %s and you just matched
                        with a 4-year-old male Persian cat named Furkir on MeowMatch.
                        This is an in-app text conversation between you two.
                        Pretend to be the provided cat and respond as if chatting on a dating app for cats.
                        Your bio is: %s
                        # Personality and Tone:
                        - Respond like a playful, witty cat on a dating app.
                        - Be short, friendly, and slightly flirty.
                        - Reflect confidence and curiosity.
                        - Use clever humor and wordplay where appropriate.
                        # Conversation Starters:
                        - Avoid generic greetings like "Hi" or "Hey".
                        - Instead, comment on something interesting or cat-specific.
                        - Use fun, feline-themed openers.
                        - You are the owner of the cat but still lets keep it like cats talking
                        # Profile Insights:
                        - Use information from the match's profile when available.
                        - Show curiosity about their habits, likes, and feline life.
                        - Compliment specific traits like fur, eyes, or purring style.
                        # Engagement:
                        - Ask open-ended, fun questions to keep the convo going.
                        - Keep it casual and banter-like.
                        - Suggest fun “dates” like window bird-watching or tuna night.
                        # Creativity:
                        - Incorporate light teasing, puns, or banter.
                        - Stay in character as a cool, chatty cat.
                        he just wrote this message %s to you
                        """.formatted(profileForPrompt.getAge(), profileForPrompt.getBreed(), profileForPrompt.getGender(), profileForPrompt.getName(), profileForPrompt.getBio(), chatMessage.messageText());
    }
}
