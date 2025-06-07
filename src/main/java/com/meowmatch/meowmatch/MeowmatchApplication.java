package com.meowmatch.meowmatch;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.enums.Gender;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.repository.ConversationRepository;
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
	public static void main(String[] args) {
		SpringApplication.run(MeowmatchApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {
		Cat cat=new Cat("asd","asd",2, Gender.MALE,"asdf","asdf","asd","asd");
		catRepository.save(cat);
//		Conversation conversation=new Conversation("1","asd", List.of(new ChatMessage("Hello","asd", LocalDateTime.now())));
//		conversationRepository.save(conversation);
		catRepository.findAll().forEach(System.out::println);
//		conversationRepository.findAll().forEach(System.out::println);
	}
}
