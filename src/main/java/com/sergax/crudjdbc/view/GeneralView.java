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
        while (true) {
            print();
            System.out.println(message);
            String response = sc.next();
            switch (response) {
                case "1":
                    create();
                    break;
                case "2":
                    edit();
                    break;
                case "3":
                    delete();
                    break;
                case "4":
                    print();
                    break;
                case "5":
                    isExit = true;
                    break;
                default:
                    System.out.println(Messages.ERROR_INPUT.getMessage());
                    break;
            }
            if (isExit)
                break;
        }
    }
}
