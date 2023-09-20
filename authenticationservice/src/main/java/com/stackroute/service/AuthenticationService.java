package com.stackroute.service;

import com.stackroute.domain.Authentication;
import com.stackroute.exception.AuthNotFoundException;

public interface AuthenticationService {

    public Authentication findByStrUserEmail(String strUserEmail) throws AuthNotFoundException;

    void saveUser(Authentication user);

}
