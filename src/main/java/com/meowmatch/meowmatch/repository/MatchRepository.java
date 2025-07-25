package com.meowmatch.meowmatch.repository;

import com.meowmatch.meowmatch.models.match.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match,String> {

}
