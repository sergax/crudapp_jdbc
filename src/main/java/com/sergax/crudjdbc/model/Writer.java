package com.sergax.crudjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Writer {
    private Long writer_id;
    private String name;
    private List<Post> posts;

    @Override
    public String toString() {
        return "Writer{" +
                "writer_id=" + writer_id +
                ", name='" + name + '\'' +
                ", posts=" + posts +
                "}\n";
    }
}
