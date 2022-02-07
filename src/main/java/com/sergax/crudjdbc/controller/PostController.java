package com.sergax.crudjdbc.controller;

import com.sergax.crudjdbc.model.Post;

import com.sergax.crudjdbc.service.PostService;

import com.sergax.crudjdbc.service.impl.PostServiceImpl;


import java.util.List;

public class PostController {
    private final PostService postService = new PostServiceImpl();

    public List<Post> getAll() {
        return postService.getAll();
    }

    public Post getById(Long id) {
        return postService.getById(id);
    }

    public Post save(Post post) {
        return postService.save(post);
    }

    public Post update(Post post) {
        return postService.update(post);
    }

    public void deleteById(Long id) {
        postService.delete(id);
    }
}
