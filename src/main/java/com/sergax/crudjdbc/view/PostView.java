package com.sergax.crudjdbc.view;

import com.sergax.crudjdbc.controller.PostController;
import com.sergax.crudjdbc.controller.TagController;
import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.model.PostStatus;
import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.utils.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView extends GeneralView {
    private PostStatus postStatus;
    private PostController postController;
    private TagController tagController;
    private Scanner sc;

    public PostView(Scanner sc, PostController postController) {
        this.message = actionList;
        this.postController = postController;
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
        postController.save(createPost());
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
                    post.setTags(selectTags());
                } else if (response == 3) {
                    post.setStatus(selectStatus());
                }
            }
        }
        return post;
    }

    private List<Tag> selectTags() {
        System.out.println(tagController.getAll());
        List<Tag> tagList = new ArrayList<>();

        Long input;
        sc = new Scanner(System.in);
        while (true) {
            input = sc.nextLong();
            if (input != 0) {
                tagList.add(tagController.getById(input));
            } else {
                break;
            }
        }
        return tagList;
    }

    private PostStatus selectStatus() {
        System.out.println("Choose status :");
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

    private Post createPost() {
        System.out.println(Messages.CONTENT.getMessage());
        String content = sc.nextLine();
        List<Tag> tagList = selectTags();
        PostStatus status = selectStatus();
        Post newPost = new Post(null, content, tagList, status);
        return newPost;
    }
}
