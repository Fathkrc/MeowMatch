package com.meowmatch.meowmatch;

import com.meowmatch.meowmatch.controller.CatController;
import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.enums.Gender;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.repository.ConversationRepository;
import com.meowmatch.meowmatch.repository.MatchRepository;
import com.meowmatch.meowmatch.repository.SwipeStateRepository;
import com.meowmatch.meowmatch.service.CatService;
import com.meowmatch.meowmatch.service.OllamaService;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MeowmatchApplication implements CommandLineRunner {
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private CatService catService;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private SwipeStateRepository swipeStateRepository;

    public static void main(String[] args) {
        SpringApplication.run(MeowmatchApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {
        catRepository.deleteAll();
        catService.seedAllCatsFromJsonFile();
        conversationRepository.deleteAll();
        matchRepository.deleteAll();
        swipeStateRepository.deleteAll();

//String request=	ollamaService.askOllama("hey are you working?"); It is working !!

    }
}
