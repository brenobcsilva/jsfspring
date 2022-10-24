import com.example.demo2.examgenerator.persistence.dao.LoginDAO;
import com.example.demo2.examgenerator.persistence.dao.ProfessorDAO;
import com.example.demo2.examgenerator.persistence.model.support.Token;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class IndexBean implements Serializable {

    private String message = "Deu certo";
    private final LoginDAO loginDAO;

    private final ProfessorDAO professorDAO;

    @Inject
    public IndexBean(LoginDAO loginDAO, ProfessorDAO professorDAO) {
        this.loginDAO = loginDAO;
        this.professorDAO = professorDAO;
    }


    public void login(){
        Token token = loginDAO.loginReturningToken("william", "devdojo");
        System.out.println(token);
    }
    public void checkProfessor(){
        professorDAO.getProfessorById(1L);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
