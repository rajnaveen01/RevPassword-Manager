package com.revpm.ui;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   Welcome to RevPassword Manager   ");
        System.out.println("====================================");

        AuthMenu authMenu = new AuthMenu();
        authMenu.start();
    }
}