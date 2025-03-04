package com.fossil.tradeshow.repository;

import com.fossil.tradeshow.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<Users, String> {

    boolean existsByEmailId(String emailId);

    Optional<Users> findByEmailId(String email);

    Optional<Users> findByEmailIdIgnoreCase(String emailId);

}
