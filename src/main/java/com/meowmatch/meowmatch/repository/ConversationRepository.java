package com.meowmatch.meowmatch.repository;

import com.meowmatch.meowmatch.models.conversations.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationRepository extends MongoRepository<Conversation,String> {

}
