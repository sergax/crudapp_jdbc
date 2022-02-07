package com.sergax.crudjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private String content;
    private List<Tag> tags;
    private PostStatus status;

}
