/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package depreriskexpertsystem;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author pactr
 */
public class AnswerRequest implements Serializable {
    private int id ;
    private String answer;

    public AnswerRequest() {
    }

    
    
    public AnswerRequest(int id, String answer) {
        this.id = id;
        this.answer = answer;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.answer);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnswerRequest other = (AnswerRequest) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.answer, other.answer)) {
            return false;
        }
        return true;
    }
    
    
}
