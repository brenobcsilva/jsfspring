package com.example.demo2.examgenerator.bean.login;

import com.example.demo2.examgenerator.custom.CustomURLEncoderDecoder;
import com.example.demo2.examgenerator.persistence.dao.LoginDAO;
import com.example.demo2.examgenerator.persistence.model.support.Token;

import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

@Named
@ViewScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private final LoginDAO loginDAO;
    private final ExternalContext externalContext;

    @Inject
    public LoginBean(LoginDAO loginDAO, ExternalContext externalContext) {
        this.loginDAO = loginDAO;
        this.externalContext = externalContext;
    }
    public String login() throws UnsupportedEncodingException {
        Token token = loginDAO.loginReturningToken(username, password);
        if(token == null)
            return null;
        addTokenAndExpirationTimeToCookies(token.getToken(), token.getExpirationTime().toString());
        return "index.xhtml?faces-redirect-true";
    }
    public String logout(){
        removeTokenExpirationTimeFromCookies();
        return "login.xhtml?faces-redirect=true";
    }

    private void addTokenAndExpirationTimeToCookies(String token, String expirationTime){
        externalContext.addResponseCookie("token",  CustomURLEncoderDecoder.encodeUTF8(token), null);
        externalContext.addResponseCookie("expirationTime", expirationTime, null);
    }

    private void removeTokenExpirationTimeFromCookies(){
        addTokenAndExpirationTimeToCookies(null, null);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
