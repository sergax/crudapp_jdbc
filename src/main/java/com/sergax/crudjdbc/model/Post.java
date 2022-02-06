package com.sergax.crudjdbc.model;

import lombok.Data;

import java.util.List;

@Data
public class Post {
    private Long id;
    private String content;
    private List<Tag> tags;
    private PostStatus status;
}
