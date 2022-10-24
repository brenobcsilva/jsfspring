package com.example.demo2.examgenerator.persistence.dao;

import com.example.demo2.examgenerator.annotation.ExceptionHandler;
import com.example.demo2.examgenerator.persistence.model.Professor;
import com.example.demo2.examgenerator.util.JsonUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.Serializable;

public class ProfessorDAO implements Serializable {

    private final String BASE_URL = "http://localhost:8085/v1/professor";
    private final JsonUtil jsonUtil;

    @Inject
    public ProfessorDAO(JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    @ExceptionHandler
    public Professor getProfessorById(long id){
        ResponseEntity<Professor> professorEntity = new RestTemplate().exchange(BASE_URL+"/1", HttpMethod.GET, new HttpEntity<>(jsonUtil.createTokenizedHeader()), Professor.class);
        Professor body = professorEntity.getBody();
        return body;
    }


}
