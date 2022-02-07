package com.sergax.crudjdbc.model;

import liquibase.pro.packaged.A;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Writer {
    private Long id;
    private String name;
    private List<Post> posts;

}
