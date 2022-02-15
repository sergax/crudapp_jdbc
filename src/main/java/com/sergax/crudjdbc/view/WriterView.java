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
    private WriterController writerController;
    private PostController postController;
    private Scanner sc;

    public WriterView(WriterController writerController, PostController postController, Scanner sc) {
        this.message = actionList;
        this.writerController = writerController;
        this.postController = postController;
        this.sc = sc;
    }

    private final String actionList = "Choose action by writers : \n" +
            "1. Create \n" +
            "2. Update \n" +
            "3. Delete \n" +
            "4. Get list \n" +
            "5. Exit \n";

    private final String printActionList = "List of writers : \n" + "ID; name; Writers";
    private final String createActionList = "Create writers . \n" + Messages.NAME.getMessage();
    private final String updateActionList = "Update writers . \n" + Messages.ID.getMessage();
    private final String deleteActionList = "Delete writers . \n" + Messages.ID.getMessage();

    @Override
    public void show() {
        boolean isExit = false;
        while (true) {
            print();
            System.out.println(actionList);
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
        System.out.println(updateActionList);
        Long id = sc.nextLong();
        Writer writer = writerController.getById(id);
        if (writer != null) {
            System.out.println(Messages.UPDATE_WRITERS.getMessage());
            Long response = sc.nextLong();
            while (true) {
                if (response == 1) {
                    System.out.println(Messages.NAME.getMessage());
                    String name = sc.nextLine();
                    writer.setName(name);
                    break;
                } else if (response == 2) {
                    writer.setPosts(selectPosts());
                    break;
                }
            }
        }
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
        System.out.println(postController.getAll());
    }

    private List<Post> selectPostsList() {
        System.out.println("Existing Posts : " + postController.getAll());
        List<Post> postList = new ArrayList<>();
        System.out.println(Messages.POST.getMessage());
        Long id = sc.nextLong();
        if (id != 0) {
            postList.add(postController.getById(id));
        }
        return postList;
    }
}
