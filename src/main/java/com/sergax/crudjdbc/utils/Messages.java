package com.sergax.crudjdbc.utils;

public enum Messages {
    DATA_DAMAGED("Data is damaged"),
    UPDATE_POST("""
            Create actions :\s
            1. Update content\s
            2. Update Status\s
            3. Exit\s
            """),
    UPDATE_WRITERS("""
            Create actions :\s
            1. Update name\s
            2. Exit\s
            """),
    ACTION_LIST("""
            Create actions :\s
            1.Tags\s
            2.Posts\s
            3.Writers\s
            4.Exit\s
            """),
    ERROR_INPUT("Wrong input, try again pleas"),
    SUCCESSFUL_OPERATION("Successful operation"),
    ERROR_OPERATION("Error!!!"),
    TAG("Put your Tag ID : "),
    POST("Put your Post ID : "),
    STATUS("Choose status : "),
    NAME("Put your name : "),
    ID("Put your ID : "),
    CONTENT("Put your content : ");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

