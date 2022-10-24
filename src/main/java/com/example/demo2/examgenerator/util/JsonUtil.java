package com.example.demo2.examgenerator.util;

import com.example.demo2.examgenerator.custom.CustomURLEncoderDecoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import javax.faces.annotation.RequestCookieMap;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.Map;

public class JsonUtil implements Serializable {


    @Inject
    @RequestCookieMap
    private Map<String, Object> cookieMap;
    public HttpHeaders createJsonHeader(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return header;
    }

    public HttpHeaders createTokenizedHeader(){
        HttpHeaders header = createJsonHeader();
        Cookie tokenCookie = (Cookie) cookieMap.get("token");
        header.add("Authorization", CustomURLEncoderDecoder.decodeUTF8(tokenCookie.getValue()));
        return header;
    }

}
