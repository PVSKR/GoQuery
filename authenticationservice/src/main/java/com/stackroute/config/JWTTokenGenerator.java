package com.stackroute.config;

import com.stackroute.domain.Authentication;

import java.util.Map;

public interface JWTTokenGenerator {
    Map<String, String> generateToken(Authentication authentication);

}
