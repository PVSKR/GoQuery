package com.stackroute.goquery.userservice.service;

import com.stackroute.goquery.userservice.Exception.UserAlreadyExistException;
import com.stackroute.goquery.userservice.domain.User;
import com.stackroute.goquery.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String addNewUser(User user) throws UserAlreadyExistException {
        Optional<User> userDb = userRepository.findByStrUserEmail(user.getStrUserEmail());
        if(userDb.isPresent()){
            throw new UserAlreadyExistException();
        }
        user.setUuidUserId(UUID.randomUUID());
        userRepository.save(user);
        return "user saved";
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserByEmailId(String email) {
        Optional<User> user = userRepository.findByStrUserEmail(email);
        return user;
    }

    @Override
    public List<User> findByUserType(String userType) {
        return userRepository.findByStrUserType(userType);
    }

    @Override
    public List<User> findByUserName(String name) {
        return userRepository.findByStrUserName(name);
    }
}

