package com.example.demo2.examgenerator.interceptor;

import com.example.demo2.examgenerator.annotation.ExceptionHandler;
import com.example.demo2.examgenerator.custom.CustomObjectMapper;
import com.example.demo2.examgenerator.persistence.model.support.ErrorDetails;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.IOException;
import java.io.Serializable;

@Interceptor
@ExceptionHandler
public class ExceptionInterceptor implements Serializable {

    private final ExternalContext externalContext;

    @Inject
    public ExceptionInterceptor(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws IOException {
        Object result = null;
        try{
            result = context.proceed();
        }catch (Exception e){
            if(e instanceof HttpClientErrorException || e instanceof HttpServerErrorException) {
                HttpStatusCodeException httpException = (HttpStatusCodeException)e;
                ErrorDetails errorDetails = new CustomObjectMapper().readValue(httpException.getResponseBodyAsString(), ErrorDetails.class);
                addMessage(FacesMessage.SEVERITY_ERROR, errorDetails.getMessage(), true);
            }else{
                e.printStackTrace();
            }
        }
        return result;
    }
    private void addMessage(FacesMessage.Severity severity, String msg, boolean keepMessages){
        final FacesMessage facesMessage = new FacesMessage(severity, msg, "");
        externalContext.getFlash().setKeepMessages(keepMessages);
        externalContext.getFlash().setRedirect(true);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}
