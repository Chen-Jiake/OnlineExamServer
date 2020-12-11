package com.onlineexam.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "test_question")
public class TestQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testQuestionId;

    private Long fkTestId;

    private Long fkQuestionId;

    public TestQuestion() {
    }

    public TestQuestion(Long fkTestId, Long fkQuestionId) {
        this.fkTestId = fkTestId;
        this.fkQuestionId = fkQuestionId;
    }

    public Long getTestQuestionId() {
        return testQuestionId;
    }

    public void setTestQuestionId(Long testQuestionId) {
        this.testQuestionId = testQuestionId;
    }

    public Long getFkTestId() {
        return fkTestId;
    }

    public void setFkTestId(Long fkTestId) {
        this.fkTestId = fkTestId;
    }

    public Long getFkQuestionId() {
        return fkQuestionId;
    }

    public void setFkQuestionId(Long fkQuestionId) {
        this.fkQuestionId = fkQuestionId;
    }

    @Override
    public String toString() {
        return "TestQuestion{" +
                "testQuestionId=" + testQuestionId +
                ", fkTestId=" + fkTestId +
                ", fkQuestionId=" + fkQuestionId +
                '}';
    }
}
