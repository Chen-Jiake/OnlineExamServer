package com.onlineexam.pojo.vo;

import com.onlineexam.pojo.Question;

import java.util.List;

public class UserQQuestionQueryVo {

    private List<List<Question>> questionList;

    private Long testId;

    private Long userQId;

    public List<List<Question>> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<List<Question>> questionList) {
        this.questionList = questionList;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Long getUserQId() {
        return userQId;
    }

    public void setUserQId(Long userQId) {
        this.userQId = userQId;
    }

    @Override
    public String toString() {
        return "UserQQuestionQueryVo{" +
                "questionList=" + questionList +
                ", testId=" + testId +
                ", userQId=" + userQId +
                '}';
    }
}
