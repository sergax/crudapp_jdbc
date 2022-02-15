package com.sergax.crudjdbc.view;

import com.sergax.crudjdbc.controller.TagController;
import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.utils.Messages;

import java.util.Scanner;

public class TagView extends GeneralView {
    private final TagController tagController;
    private Scanner sc;

    public TagView(TagController tagController, Scanner sc) {
        this.message = actionList;
        this.tagController = tagController;
        this.sc = sc;
    }

    private final String actionList = """
            Choose action by tags :\s
            1. Create\s
            2. Update\s
            3. Delete\s
            4. Get list\s
            5. Exit\s
            """;

    private final String printActionList = "List of tags : \n" + "ID; name";
    private final String createActionList = "Create tag . \n" + Messages.NAME.getMessage();
    private final String updateActionList = "Update tag . \n" + Messages.ID.getMessage();
    private final String deleteActionList = "Delete tag . \n" + Messages.ID.getMessage();

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
    public void create() {
        System.out.println(createActionList);
        String name = sc.next();
        tagController.create(new Tag(null, name));
        System.out.println(Messages.SUCCESSFUL_OPERATION.getMessage());
    }

    @Override
    public void edit() {
        System.out.println(updateActionList);
        Long id = Long.parseLong(String.valueOf(sc.nextLong()));
        System.out.println(Messages.NAME.getMessage());
        String name = sc.next();
        tagController.update(new Tag(id, name));
        System.out.println(Messages.SUCCESSFUL_OPERATION.getMessage());
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