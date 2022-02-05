package com.sergax.crudjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Post {
    private int id;
    private String content;
    private List<Tag> tags;
    private PostStatus status;
}
