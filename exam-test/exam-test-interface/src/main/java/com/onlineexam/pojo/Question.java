package com.onlineexam.pojo;

import com.onlineexam.pojo.Subject;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private String questionTitle;

    private Integer questionType;  //试题类型，0：单选，1：多选，2：判断

    private String questionSelectA;

    private String questionSelectB;

    private String questionSelectC;

    private String questionSelectD;

    private String questionYesanswer;

    private Integer questionScore;

    private Integer questionLevel;     //难易程度，0：简单，1：中等，2：高难

    private Date questionAddtime;

    private Long fkQuestionSubjectId;

    private Subject fkSubject;

    public Question() {
    }

    public Question(String questionTitle, Integer questionType, String questionSelectA, String questionSelectB, String questionSelectC, String questionSelectD, String questionYesanswer, Integer questionScore, Integer questionLevel, Date questionAddtime, Long fkQuestionSubjectId, Subject fkSubject) {
        this.questionTitle = questionTitle;
        this.questionType = questionType;
        this.questionSelectA = questionSelectA;
        this.questionSelectB = questionSelectB;
        this.questionSelectC = questionSelectC;
        this.questionSelectD = questionSelectD;
        this.questionYesanswer = questionYesanswer;
        this.questionScore = questionScore;
        this.questionLevel = questionLevel;
        this.questionAddtime = questionAddtime;
        this.fkQuestionSubjectId = fkQuestionSubjectId;
        this.fkSubject = fkSubject;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getQuestionSelectA() {
        return questionSelectA;
    }

    public void setQuestionSelectA(String questionSelectA) {
        this.questionSelectA = questionSelectA;
    }

    public String getQuestionSelectB() {
        return questionSelectB;
    }

    public void setQuestionSelectB(String questionSelectB) {
        this.questionSelectB = questionSelectB;
    }

    public String getQuestionSelectC() {
        return questionSelectC;
    }

    public void setQuestionSelectC(String questionSelectC) {
        this.questionSelectC = questionSelectC;
    }

    public String getQuestionSelectD() {
        return questionSelectD;
    }

    public void setQuestionSelectD(String questionSelectD) {
        this.questionSelectD = questionSelectD;
    }

    public String getQuestionYesanswer() {
        return questionYesanswer;
    }

    public void setQuestionYesanswer(String questionYesanswer) {
        this.questionYesanswer = questionYesanswer;
    }

    public Integer getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(Integer questionScore) {
        this.questionScore = questionScore;
    }

    public Integer getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(Integer questionLevel) {
        this.questionLevel = questionLevel;
    }

    public Date getQuestionAddtime() {
        return questionAddtime;
    }

    public void setQuestionAddtime(Date questionAddtime) {
        this.questionAddtime = questionAddtime;
    }

    public Long getFkQuestionSubjectId() {
        return fkQuestionSubjectId;
    }

    public void setFkQuestionSubjectId(Long fkQuestionSubjectId) {
        this.fkQuestionSubjectId = fkQuestionSubjectId;
    }

    public Subject getFkSubject() {
        return fkSubject;
    }

    public void setFkSubject(Subject fkSubject) {
        this.fkSubject = fkSubject;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", questionTitle='" + questionTitle + '\'' +
                ", questionType=" + questionType +
                ", questionSelectA='" + questionSelectA + '\'' +
                ", questionSelectB='" + questionSelectB + '\'' +
                ", questionSelectC='" + questionSelectC + '\'' +
                ", questionSelectD='" + questionSelectD + '\'' +
                ", questionYesanswer='" + questionYesanswer + '\'' +
                ", questionScore=" + questionScore +
                ", questionLevel=" + questionLevel +
                ", questionAddtime=" + questionAddtime +
                ", fkQuestionSubjectId=" + fkQuestionSubjectId +
                ", fkSubject=" + fkSubject +
                '}';
    }
}
