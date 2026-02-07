package com.revpm.ui;

public class MainApp {
    public static void main(String[] args) {
    	System.out.println("\n");
        System.out.println("  +--------------------------------------------------------+");
        System.out.println("  |         R E V   P A S S W O R D   M A N A G E R        |");
        System.out.println("  |               Secure. Encrypted. Reliable.             |");
        System.out.println("  +--------------------------------------------------------+");
        System.out.println("");

        AuthMenu authMenu = new AuthMenu();
        authMenu.start();
    }
}