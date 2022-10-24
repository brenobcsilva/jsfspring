package com.example.demo2.examgenerator.persistence.dao;

import com.example.demo2.examgenerator.annotation.ExceptionHandler;
import com.example.demo2.examgenerator.custom.CustomRestTemplate;
import com.example.demo2.examgenerator.persistence.model.support.Token;
import com.example.demo2.examgenerator.util.JsonUtil;
import org.springframework.http.*;

import javax.inject.Inject;
import java.io.Serializable;

public class LoginDAO implements Serializable {

    private final String BASE_URL = "http://localhost:8085/login";
    private final CustomRestTemplate customRestTemplate;

    private final JsonUtil jsonUtil;
    @Inject
    public LoginDAO(CustomRestTemplate customRestTemplate, JsonUtil jsonUtil) {
        this.customRestTemplate = customRestTemplate;
        this.jsonUtil = jsonUtil;
    }

    @ExceptionHandler
    public Token loginReturningToken(String username, String password){
        String loginJson = "{\"username\":"+addQuotes(username) + ", \"password\":"+addQuotes(password)+"}";
            ResponseEntity<Token> tokenExchange = customRestTemplate.exchange(BASE_URL, HttpMethod.POST, new HttpEntity<>(loginJson, jsonUtil.createJsonHeader()), Token.class);
            return tokenExchange.getBody();
    }
    private String addQuotes(String value){
        return new StringBuilder(300).append("\"").append(value).append("\"").toString();
    }

}
