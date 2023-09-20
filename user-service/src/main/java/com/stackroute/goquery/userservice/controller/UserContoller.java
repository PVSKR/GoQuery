package com.stackroute.goquery.userservice.controller;

import com.stackroute.goquery.userservice.Exception.UserAlreadyExistException;
import com.stackroute.goquery.userservice.Exception.UserNotFound;
import com.stackroute.goquery.userservice.domain.User;
import com.stackroute.goquery.userservice.service.RabbitMQSender;
import com.stackroute.goquery.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/registration")
@CrossOrigin(value = "*")
public class UserContoller{

    private UserService userService;
    private RabbitMQSender rabbitMQSender;

    @Autowired
    public UserContoller(UserService userService, RabbitMQSender rabbitMQSender) {
        this.userService = userService;
        this.rabbitMQSender=rabbitMQSender;
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addNewUser(@RequestBody User user ) throws UserAlreadyExistException {
         try {
             userService.addNewUser(user);
             rabbitMQSender.sendUser(user);
             return new ResponseEntity<User>(user, HttpStatus.CREATED);
         }
         catch (UserAlreadyExistException uaee){
             return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
         }
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
            return userService.getAllUsers();
    }

    @GetMapping("/findByEmail/{email}")
    public User findUserByEmailId(@PathVariable String email) throws UserNotFound {
        try {
            Optional<User> user = userService.findUserByEmailId(email);
            return user.get();
        } catch (Exception e) {
            throw new UserNotFound("not found");
        }
    }

    @GetMapping("/findByType/{type}")
    public List<User> findByUserType(@PathVariable String type){
        return userService.findByUserType(type);
    }

    @GetMapping("/findByUserName/{name}")
    public List<User> findByUserName(@PathVariable String name){
        return userService.findByUserName(name);
    }

}




