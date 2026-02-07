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
            System.out.println("\n================ SECURITY SETTINGS ===================");
            System.out.println("   1. Add New Security Question");
            System.out.println("   2. View Active Questions");
            System.out.println("   3. Delete Question");
            System.out.println("   0. Return to Dashboard");
            System.out.println("======================================================");

            int choice = InputUtil.nextInt(">> Select Option: ");

            switch (choice) {
                case 1: addQuestion(); break;
                case 2: listQuestions(); break;
                case 3: deleteQuestion(); break;
                case 0: return;
                default: System.out.println(">> [ERROR] Invalid choice.");
            }
        }
    }

    private void addQuestion() {
        System.out.println("\n-- Setup Recovery Question --");
        String question;
        while(true) {
            question = InputUtil.nextLine(">> Question: ");
            if (!question.trim().isEmpty()) break;
            System.out.println(">> [ERROR] Question text required.");
        }

        String answer;
        while(true) {
            answer = InputUtil.nextLine(">> Answer: ");
            if (!answer.trim().isEmpty()) break;
            System.out.println(">> [ERROR] Answer text required.");
        }

        if (securityService.addQuestion(userId, question, answer)) {
            System.out.println(">> [SUCCESS] Security question added.");
        } else {
            System.out.println(">> [ERROR] Failed. Question might differ.");
        }
    }

    private void listQuestions() {
        List<SecurityQuestion> list = securityService.getQuestions(userId);
        System.out.println("\n----------------- ACTIVE QUESTIONS -------------------");
        for (SecurityQuestion q : list) {
            System.out.printf("   [ID: %d] %s%n", q.getQuestionId(), q.getQuestion());
        }
        System.out.println("------------------------------------------------------");
    }

    private void deleteQuestion() {
        long questionId = InputUtil.nextLong(">> Enter Question ID to Remove: ");
        if (securityService.deleteQuestion(questionId)) {
            System.out.println(">> [SUCCESS] Question removed.");
        } else {
            System.out.println(">> [ERROR] Removal failed. Invalid ID.");
        }
    }
}