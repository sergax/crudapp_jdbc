package com.sergax.crudjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long post_id;
    private String content;
    private List<Tag> tags;
    private PostStatus status;

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", status=" + status +
                "}\n";
    }
}
