package com.sergax.crudjdbc.repository.jdbc;

import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.repository.PostRepository;

import java.util.List;
import java.sql.*;

public class JdbcPostImpl implements PostRepository {
//    private final SqlUpdate = "update post set name = ? where id = ?";
    @Override
    public Post getId(Long id) {
        return null;
    }

    @Override
    public void deleteBuID(Long id) {

    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public List<Post> getAll() {
        return null;
    }
}
