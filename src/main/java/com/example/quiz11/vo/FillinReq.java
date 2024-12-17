package com.example.quiz11.vo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class FillinReq {

    private int quizId;

    private String userName;

    private String phone;

    private String email;

    private int age;
    // 題號，問題
    private Map<Integer, List<String>> answer;

    private LocalDate fillinDate;

    public FillinReq() {
    }

    public FillinReq(int quizId, String userName, String phone, String email, int age, Map<Integer, List<String>> answer, LocalDate fillinDate) {
        this.quizId = quizId;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.answer = answer;
        this.fillinDate = fillinDate;
    }

    public int getQuizId() {
        return quizId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public Map<Integer, List<String>> getAnswer() {
        return answer;
    }

    public LocalDate getFillinDate() {
        return fillinDate;
    }
}
