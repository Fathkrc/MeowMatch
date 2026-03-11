package com.meowmatch.meowmatch.repository;

import com.meowmatch.meowmatch.models.Cat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatRepository extends MongoRepository<Cat,String> {

    Optional<Cat> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
