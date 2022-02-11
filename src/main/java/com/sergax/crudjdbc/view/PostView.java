package com.sergax.crudjdbc.view;

import com.sergax.crudjdbc.controller.PostController;
import com.sergax.crudjdbc.controller.TagController;
import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.model.PostStatus;
import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.repository.jdbc.JdbcTagImpl;
import com.sergax.crudjdbc.utils.Messages;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView extends GeneralView {
    private PostStatus postStatus;
    private PostController postController;
    private TagController tagController;
    private Scanner sc;

    public PostView(Scanner sc, PostController postController, TagController tagController) {
        this.message = actionList;
        this.postController = postController;
        this.tagController = tagController;
        this.sc = sc;
    }

    private final String actionList = "Choose action by posts : \n" +
            "1. Create \n" +
            "2. Update \n" +
            "3. Delete \n" +
            "4. Get list \n" +
            "5. Exit \n";

    private final String printActionList = "List of posts : \n" + "ID; content; Tags; status";
    private final String createActionList = "Create post . \n" + Messages.CONTENT.getMessage();
    private final String updateActionList = "Update post . \n" + Messages.ID.getMessage();
    private final String deleteActionList = "Delete post . \n" + Messages.ID.getMessage();

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
    public void create() {
        System.out.println(createActionList);
        String content = sc.next();
        List<Tag> tagList = selectTags();
        PostStatus status = PostStatus.ACTIVE;
        postController.create(new Post(null, content, tagList, status));
        System.out.println(Messages.SUCCESSFUL_OPERATION.getMessage());
    }

    @Override
    public void edit() {
        postController.update(updatePost());
        System.out.println(Messages.SUCCESSFUL_OPERATION.getMessage());
    }

    @Override
    public void delete() {
        System.out.println(deleteActionList);
        Long id = sc.nextLong();
        try {
            postController.deleteById(id);
            System.out.println(Messages.SUCCESSFUL_OPERATION.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.err.println(Messages.ERROR_OPERATION.getMessage());
        }
    }

    @Override
    public void print() {
        System.out.println(printActionList);
        System.out.println(postController.getAll());
    }

    private Post updatePost() {
        System.out.println(updateActionList);
        Long id = sc.nextLong();
        Post post = postController.getById(id);
        if (post != null) {
            System.out.println(Messages.UPDATE_POST.getMessage());
            Long response = sc.nextLong();
            while (true) {
                if (response == 1) {
                    System.out.println(Messages.CONTENT.getMessage());
                    String content = sc.nextLine();
                    post.setContent(content);
                    break;
                } else if (response == 2) {
//                    post.setTags(selectTags());
                } else if (response == 3) {
                    post.setStatus(selectStatus());
                }
            }
        }
        return post;
    }

    private List<Tag> selectTags() {
        System.out.println("Existing Tags" + tagController.getAll());
        List<Tag> tagList = new ArrayList<>();
        System.out.println(Messages.TAG.getMessage());
        Long input;
        sc = new Scanner(System.in);
        input = Long.parseLong(sc.nextLine());
        if (input != 0) {
            tagList.add(tagController.getById(input));
        }
        return tagList;
    }

    private PostStatus selectStatus() {
        System.out.println(Messages.STATUS.getMessage());
        System.out.println("1. ACTIVE, \n" +
                "2. DELETED. \n");
        Long input = sc.nextLong();
        while (true) {
            if (input == 1) {
                postStatus = PostStatus.ACTIVE;
                break;
            } else if (input == 2) {
                postStatus = PostStatus.DELETED;
                break;
            }
        }
        return postStatus;
    }

    private String selectNameContent() {
        String name = sc.nextLine();
        return name;
    }
}
