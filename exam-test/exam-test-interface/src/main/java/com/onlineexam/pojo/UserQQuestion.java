package com.onlineexam.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user_q_question")
public class UserQQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userQQuestionId;

    private String userQQuestionUseranswer;

    private Long fkQuestionId;

    private Question fkQuestion;

    private Long fkTestId;

    private Test fkTest;

    private Long fkUserQId;

    private UserQ fkUserQ;

    public UserQQuestion() {
    }

    public Long getUserQQuestionId() {
        return userQQuestionId;
    }

    public void setUserQQuestionId(Long userQQuestionId) {
        this.userQQuestionId = userQQuestionId;
    }

    public String getUserQQuestionUseranswer() {
        return userQQuestionUseranswer;
    }

    public void setUserQQuestionUseranswer(String userQQuestionUseranswer) {
        this.userQQuestionUseranswer = userQQuestionUseranswer;
    }

    public Long getFkQuestionId() {
        return fkQuestionId;
    }

    public void setFkQuestionId(Long fkQuestionId) {
        this.fkQuestionId = fkQuestionId;
    }

    public Question getFkQuestion() {
        return fkQuestion;
    }

    public void setFkQuestion(Question fkQuestion) {
        this.fkQuestion = fkQuestion;
    }

    public Long getFkTestId() {
        return fkTestId;
    }

    public void setFkTestId(Long fkTestId) {
        this.fkTestId = fkTestId;
    }

    public Test getFkTest() {
        return fkTest;
    }

    public void setFkTest(Test fkTest) {
        this.fkTest = fkTest;
    }

    public Long getFkUserQId() {
        return fkUserQId;
    }

    public void setFkUserQId(Long fkUserQId) {
        this.fkUserQId = fkUserQId;
    }

    public UserQ getFkUserQ() {
        return fkUserQ;
    }

    public void setFkUserQ(UserQ fkUserQ) {
        this.fkUserQ = fkUserQ;
    }

    @Override
    public String toString() {
        return "UserQQuestion{" +
                "userQQuestionId=" + userQQuestionId +
                ", userQQuestionUseranswer='" + userQQuestionUseranswer + '\'' +
                ", fkQuestionId=" + fkQuestionId +
                ", fkQuestion=" + fkQuestion +
                ", fkTestId=" + fkTestId +
                ", fkTest=" + fkTest +
                ", fkUserQId=" + fkUserQId +
                ", fkUserQ=" + fkUserQ +
                '}';
    }
}
