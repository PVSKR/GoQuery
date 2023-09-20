package com.stackroute.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.stackroute.domain.Authentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTTokenGeneratorImpl implements JWTTokenGenerator {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${app.jwttoken.message}")
    private String message;

    @Override
    public Map<String, String> generateToken(Authentication authentication) {
        String jwtToken = "";
        jwtToken= Jwts.builder().setSubject(authentication.getStrUserEmail()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, secret).compact();
        Map<String, String> jwtTokenMap=new HashMap<>();
        jwtTokenMap.put("token", jwtToken);
        jwtTokenMap.put("userEmail", authentication.getStrUserEmail());
        jwtTokenMap.put("message", message);
        return jwtTokenMap;
    }
}
