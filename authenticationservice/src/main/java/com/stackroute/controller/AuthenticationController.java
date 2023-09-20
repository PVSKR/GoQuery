package com.stackroute.controller;

import com.stackroute.config.JWTTokenGenerator;
import com.stackroute.domain.Authentication;
import com.stackroute.exception.AuthNotFoundException;
import com.stackroute.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(value = "*")
public class AuthenticationController {

    @Value("${app.controller.exception.message1}")
    private String message1;

    @Value("${app.controller.exception.message2}")
    private String message2;

    @Value("${app.controller.exception.message3}")
    private String message3;

    private AuthenticationService authenticationService;
    private JWTTokenGenerator jwtTokenGenerator;

    ResponseEntity<?> responseEntity;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JWTTokenGenerator jwtTokenGenerator) {
        this.authenticationService = authenticationService;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

        @PostMapping("login")
        public ResponseEntity<?> loginUser(@RequestBody Authentication authentication){
            try {
                if (authentication.getStrUserEmail() == null || authentication.getStrpassword() == null
                || authentication.getStrUserEmail().isBlank() || authentication.getStrpassword().isBlank() ) {
                    throw new AuthNotFoundException(message1);
                }
                Authentication authDetails = authenticationService.findByStrUserEmail(authentication.getStrUserEmail());
                if (authDetails == null) {
                    throw new AuthNotFoundException(message2);
                }
                if (!(authentication.getStrpassword().equals(authDetails.getStrpassword()))) {
                    throw new AuthNotFoundException(message3);
                }
                responseEntity = new ResponseEntity<>(jwtTokenGenerator.generateToken(authDetails), HttpStatus.OK);
            } catch (AuthNotFoundException e) {
                responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }
            return responseEntity;
        }
    }
