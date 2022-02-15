package com.sergax.crudjdbc.view;

import com.sergax.crudjdbc.controller.PostController;
import com.sergax.crudjdbc.controller.WriterController;
import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.model.Writer;
import com.sergax.crudjdbc.utils.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView extends GeneralView {
    private final WriterController writerController;
    private final PostController postController;
    private Scanner sc;

    public WriterView(WriterController writerController, PostController postController, Scanner sc) {
        this.message = actionList;
        this.writerController = writerController;
        this.postController = postController;
        this.sc = sc;
    }

    private final String actionList = """
            Choose action by writers :\s
            1. Create\s
            2. Update\s
            3. Delete\s
            4. Get list\s
            5. Exit\s
            """;

    private final String printActionList = "List of writers : \n" + "ID; name; Writers";
    private final String createActionList = "Create writers . \n" + Messages.NAME.getMessage();
    private final String updateActionList = "Update writers . \n" + Messages.ID.getMessage();
    private final String deleteActionList = "Delete writers . \n" + Messages.ID.getMessage();

    @Override
    public void show() {
        boolean isExit = false;
        do {
            print();
            System.out.println(actionList);
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

    @Override
    void create() {
        System.out.println(createActionList);
        String name = sc.next();
        List<Post> posts = selectPostsList();
        writerController.create(new Writer(null, name, posts));
        System.out.println(Messages.SUCCESSFUL_OPERATION.getMessage());
    }

    @Override
    void edit() {
        writerController.update(updateWriters());
        System.out.println(Messages.SUCCESSFUL_OPERATION.getMessage());
    }

    private Writer updateWriters() {
        System.out.println(updateActionList);
        Long id = sc.nextLong();
        Writer writer = writerController.getById(id);
        if (writer != null) {
            System.out.println(Messages.UPDATE_WRITERS.getMessage());
            Long response = sc.nextLong();
            while (true) {
                if (response == 1) {
                    System.out.println(Messages.NAME.getMessage());
                    String name = sc.next();
                    writer.setName(name);
                    break;
                } else if (response == 2) {
                    writer.setPosts(selectPosts());
                    break;
                }
            }
        }
        return writer;
    }

    private List<Post> selectPosts() {
        List<Post> postList = new ArrayList<>();
        System.out.println(Messages.POST.getMessage());
        sc = new Scanner(System.in);
        Long input = Long.valueOf(sc.nextLine());
        if (input != 0) {
            postList.add(postController.getById(input));
        }
        return postList;
    }

    @Override
    void delete() {
        System.out.println(deleteActionList);
        Long id = sc.nextLong();
        writerController.deleteById(id);
        System.out.println(Messages.SUCCESSFUL_OPERATION.getMessage());
    }

    @Override
    void print() {
        System.out.println(printActionList);
        System.out.println(writerController.getAll());
    }

    private List<Post> selectPostsList() {
        System.out.println("Existing Posts : " + postController.getAll());
        List<Post> postList = new ArrayList<>();
        System.out.println(Messages.POST.getMessage());
        sc = new Scanner(System.in);
        Long id = Long.valueOf(sc.nextLine());
        if (id != 0) {
            postList.add(postController.getById(id));
        }
        return postList;
    }
}
