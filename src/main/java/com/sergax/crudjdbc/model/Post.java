package com.sergax.crudjdbc.model;

import java.util.List;

public class Post {
    private int id;
    private String content;
    private List<Tag> tags;
    private PostStatus status;
}
