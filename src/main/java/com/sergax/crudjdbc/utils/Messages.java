package com.sergax.crudjdbc.utils;

public enum Messages {

    DATA_DAMAGED("Data is damaged"),
    UPDATE_POST("""
            Create actions :\s
            1. Update content\s
            2. Update Tags\s
            3. Update Status\s
            4. Exit\s
            """),
    ACTION_LIST("""
            Create actions :\s
            1.Tags\s
            2.Posts\s
            3.Writers\s
            4.Exit\s
            """),
    DELETED_STATUS("You can't edit, post was DELETE"),
    ACTIVE_STATUS("You can't edit, post is ACTIVE"),
    ERROR_INPUT("Wrong input, try again pleas"),
    EMPTY_LIST("List is empty"),
    SUCCESSFUL_OPERATION("Successful operation"),
    ERROR_OPERATION("Error!!!"),
    TAG("Put your Tag ID : "),
    STATUS("Choose status : "),
    NAME("Put your name : "),
    ID("Put your ID : "),
    NOT_FIND_ID("Not found ID "),
    CONTENT("Put your content : ");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

