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
public class AnswerRequest {

    private String answers;
    private int answersId;

    public AnswerRequest() {
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public int getAnswersId() {
        return answersId;
    }

    public void setAnswersId(int answersId) {
        this.answersId = answersId;
    }


}
