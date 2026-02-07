package com.revpm.model;

public class SecurityQuestion {

    private long questionId;
    private long userId;
    private String question;
    private String answer; 

    public SecurityQuestion() {}

    public SecurityQuestion(long userId, String question, String answer) {
        this.userId = userId;
        this.question = question;
        this.answer = answer;
    }

    public SecurityQuestion(long questionId, long userId, String question, String answer) {
        this.questionId = questionId;
        this.userId = userId;
        this.question = question;
        this.answer = answer;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "SecurityQuestion{" +
                "questionId=" + questionId +
                ", userId=" + userId +
                ", question='" + question + '\'' +
                '}';
    }
}
