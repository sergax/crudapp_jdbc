package com.sergax.crudjdbc.controller;

import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.service.PostService;
import com.sergax.crudjdbc.service.impl.PostServiceImpl;

import java.util.List;

public class PostController {
    PostService postService = new PostServiceImpl();

    public List<Post> getAll() {
        return postService.getAll();
    }

    public Post getById(Long id) {
        return postService.getById(id);
    }

    public Post create(Post post) {
        return postService.create(post);
    }

    public Post update(Post post) {
        return postService.update(post);
    }

    public void deleteById(Long id) {
        postService.delete(id);
    }
}
