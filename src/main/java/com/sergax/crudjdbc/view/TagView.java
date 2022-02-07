package com.sergax.crudjdbc.view;

import com.sergax.crudjdbc.controller.TagController;
import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.utils.Messages;

import java.util.Scanner;

public class TagView extends GeneralView {
    private TagController tagController;
    private Scanner sc;

    public TagView(TagController tagController, Scanner sc) {
        this.message = actionList;
        this.tagController = tagController;
        this.sc = sc;
    }

    private final String actionList = "Choose action by tags : \n" +
            "1. Create \n" +
            "2. Update \n" +
            "3. Delete \n" +
            "4. Get list \n" +
            "5. Exit \n";

    private final String printActionList = "List of tags : \n" + "ID; name";
    private final String createActionList = "Create tag . \n" + Messages.NAME.getMessage();
    private final String updateActionList = "Update tag . \n" + Messages.ID.getMessage();
    private final String deleteActionList = "Delete tag . \n" + Messages.ID.getMessage();

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
        String name = sc.next();
        tagController.save(new Tag(null, name));
        System.out.println(Messages.SUCCESSFUL_OPERATION.getMessage());
    }

    @Override
    public void edit() {
        System.out.println(updateActionList);
        Long id = sc.nextLong();
        if(id != null) {
            System.out.println(Messages.NAME.getMessage());
            String name = sc.nextLine();
            tagController.update(new Tag(id, name));
            System.out.println(Messages.SUCCESSFUL_OPERATION.getMessage());
        } else {
            System.out.println(Messages.ERROR_INPUT.getMessage());
        }
    }

    @Override
    public void delete() {
        System.out.println(deleteActionList);
        Long id = sc.nextLong();
        tagController.deleteById(id);
        System.out.println(Messages.SUCCESSFUL_OPERATION.getMessage());
    }

    @Override
    public void print() {
        System.out.println(printActionList);
        System.out.println(tagController.getAll());
    }
}