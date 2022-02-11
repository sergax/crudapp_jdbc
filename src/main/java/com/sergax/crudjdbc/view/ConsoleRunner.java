package com.sergax.crudjdbc.view;

import com.sergax.crudjdbc.controller.PostController;
import com.sergax.crudjdbc.controller.TagController;
import com.sergax.crudjdbc.controller.WriterController;
import com.sergax.crudjdbc.utils.Messages;

import java.util.Scanner;

public class ConsoleRunner {
    GeneralView writerView;
    GeneralView postView;
    GeneralView tagView;

    private Scanner sc = new Scanner(System.in);

    public ConsoleRunner() {
        try {
            TagController tagController = new TagController();
            PostController postController = new PostController();
            WriterController writerController = new WriterController();

            tagView = new TagView(tagController, sc);
            postView = new PostView(sc, postController, tagController);
//            writerView = new WriterView(sc, writerController);

        } catch (Exception e) {
            System.err.println(Messages.DATA_DAMAGED.getMessage());
        }
    }

    public void run() {
        boolean isExit = false;
        while (true) {
            System.out.println(Messages.ACTION_LIST.getMessage());
            String response = sc.next();
            switch (response) {
                case "1":
                    tagView.show();
                    break;
                case "2":
                    postView.show();
                    break;
                case "3":
                    writerView.show();
                    break;
                case "4":
                    isExit = true;
                    break;
                default:
                    System.out.println(Messages.ERROR_INPUT.getMessage());
                    break;
            }
            if (isExit)
                break;
        }
        sc.close();
    }

    public static void main(String[] args) {
        try {
            ConsoleRunner runner = new ConsoleRunner();
            runner.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
