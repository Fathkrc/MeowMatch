package com.meowmatch.meowmatch.repository;

import com.meowmatch.meowmatch.models.Cat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends MongoRepository<Cat,String> {


}
