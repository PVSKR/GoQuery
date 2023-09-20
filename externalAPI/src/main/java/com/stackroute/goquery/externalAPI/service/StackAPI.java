package com.stackroute.goquery.externalAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StackAPI {
    private final RestTemplate restTemplate;
    @Autowired
    public StackAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public String consumeAPI(String url){
        return restTemplate.getForObject(url,String.class);
    }
}
