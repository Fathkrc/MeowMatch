package com.meowmatch.meowmatch.repository;

import com.meowmatch.meowmatch.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
