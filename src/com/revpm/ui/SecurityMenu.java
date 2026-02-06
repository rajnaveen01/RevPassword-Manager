package com.revpm.ui;

import com.revpm.model.SecurityQuestion;
import com.revpm.service.SecurityService;
import com.revpm.util.InputUtil;
import java.util.List;

public class SecurityMenu {

    private SecurityService securityService = new SecurityService();
    private long userId;

    public SecurityMenu(long userId) {
        this.userId = userId;
    }

    public void start() {
        while (true) {
            System.out.println("\n--- SECURITY MENU ---");
            System.out.println("1. Add Question");
            System.out.println("2. List Questions");
            System.out.println("3. Delete Question");
            System.out.println("0. Back");

            int choice = InputUtil.nextInt("Choose option: ");

            switch (choice) {
                case 1: addQuestion(); break;
                case 2: listQuestions(); break;
                case 3: deleteQuestion(); break;
                case 0: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private void addQuestion() {
        System.out.println("\n--- ADD SECURITY QUESTION ---");
        
        String question;
        while(true) {
            question = InputUtil.nextLine("Question: ");
            if (!question.trim().isEmpty()) break;
            System.out.println("Error: Question cannot be empty!");
        }

        String answer;
        while(true) {
            answer = InputUtil.nextLine("Answer: ");
            if (!answer.trim().isEmpty()) break;
            System.out.println("Error: Answer cannot be empty!");
        }

        boolean success = securityService.addQuestion(userId, question, answer);
        System.out.println(success ? "Added!" : "Failed!");
    }

    private void listQuestions() {
        List<SecurityQuestion> list = securityService.getQuestions(userId);
        System.out.printf("%-5s | %-30s%n", "ID", "Question");
        System.out.println("-------------------------------------");
        for (SecurityQuestion q : list) {
            System.out.printf("%-5d | %-30s%n", q.getQuestionId(), q.getQuestion());
        }
    }

    private void deleteQuestion() {
        long questionId = InputUtil.nextLong("Question ID: ");
        boolean success = securityService.deleteQuestion(questionId);
        System.out.println(success ? "Deleted!" : "Failed!");
    }
}