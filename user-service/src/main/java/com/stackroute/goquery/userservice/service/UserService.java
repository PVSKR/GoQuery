package com.stackroute.goquery.userservice.service;

import com.stackroute.goquery.userservice.Exception.UserAlreadyExistException;
import com.stackroute.goquery.userservice.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService{
    String addNewUser(User user) throws UserAlreadyExistException;

    List<User> getAllUsers();

    Optional<User> findUserByEmailId(String email);

    List<User> findByUserType(String userType);

    List<User> findByUserName(String name);
}
