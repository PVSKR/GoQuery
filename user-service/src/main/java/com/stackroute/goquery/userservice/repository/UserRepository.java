package com.stackroute.goquery.userservice.repository;

import com.stackroute.goquery.userservice.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID>{

    Optional<User> findByStrUserEmail(String email);

    List<User> findByStrUserType(String userType);

    List<User> findByStrUserName(String name);
}
