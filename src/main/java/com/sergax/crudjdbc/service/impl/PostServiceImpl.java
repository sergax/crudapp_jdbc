package com.sergax.crudjdbc.service.impl;

import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.repository.PostRepository;
import com.sergax.crudjdbc.repository.jdbc.JdbcPostImpl;
import com.sergax.crudjdbc.service.PostService;

import java.util.List;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository = new JdbcPostImpl();

    @Override
    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getAll() {
       return postRepository.getAll();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) {
        return postRepository.update(post);
    }
}
