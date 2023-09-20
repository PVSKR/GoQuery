package com.stackroute.service;

import com.stackroute.domain.Authentication;
import com.stackroute.exception.AuthNotFoundException;
import com.stackroute.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationRepository authenticationRepository;

    @Value("${app.service.message1}")
    private String message1;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public Authentication findByStrUserEmail(String strUserEmail) throws AuthNotFoundException {
        Optional<Authentication> authUser = authenticationRepository.findById(strUserEmail);
        if (authUser.isPresent()){
            return authUser.get();
        } else {
            throw new AuthNotFoundException(message1);
        }
    }

    @Override
    public void saveUser(Authentication user) {
        authenticationRepository.save(user);
    }
}
