package com.sergax.crudjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Writer {
    private int id;
    private String name;
    private List<Post> posts;
}
