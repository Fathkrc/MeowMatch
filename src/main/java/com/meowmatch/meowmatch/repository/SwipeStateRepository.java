package com.meowmatch.meowmatch.repository;

import com.meowmatch.meowmatch.models.SwipeState;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SwipeStateRepository extends MongoRepository<SwipeState,String> {

}
