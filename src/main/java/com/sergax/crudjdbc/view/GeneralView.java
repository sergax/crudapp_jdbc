package com.sergax.crudjdbc.view;

import com.sergax.crudjdbc.utils.Messages;

import java.util.Scanner;

public abstract class GeneralView {

    protected String message;
    protected Scanner sc;

    abstract void create();

    abstract void edit();

    abstract void delete();

    abstract void print();

    void show() {
        boolean isExit = false;
        do {
            print();
            System.out.println(message);
            String response = sc.next();
            switch (response) {
                case "1" -> create();
                case "2" -> edit();
                case "3" -> delete();
                case "4" -> print();
                case "5" -> isExit = true;
                default -> System.out.println(Messages.ERROR_INPUT.getMessage());
            }
        } while (!isExit);
    }
}
