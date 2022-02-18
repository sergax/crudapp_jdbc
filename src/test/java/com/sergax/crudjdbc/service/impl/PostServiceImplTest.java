package com.sergax.crudjdbc.service.impl;

import com.sergax.crudjdbc.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Spy
    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById() {
        Post post = postService.getById(1L);
        assertNotNull(post);
        assertEquals(1L, post.getPost_id());
        Mockito.verify(postService, Mockito.times(1)).getById(Mockito.anyLong());
    }

    @Test
    void delete() {
    }

    @Test
    void get_all_Post() {
        List<Post> postList = postService.getAll();
    }

    @Test
    void create() {
        Post newPost = new Post(null, "Content", new ArrayList<>(), null );
    }

    @Test
    void update() {
        Post updatePost = postService.getById(1L);
        String content = updatePost.getContent();
        String newContent = "Content 1";
        //Status updating need to add
        updatePost.setContent(newContent);
        Post updatedPost = postService.update(updatePost);

        assertNotNull(updatedPost);
        assertEquals(updatePost.getPost_id(), updatedPost.getPost_id());
        //getUpdated ???
//        assertNotNull(updatedPost.);
        assertEquals(newContent, updatedPost.getContent());
        Mockito.verify(postService, Mockito.times(1)).update(Mockito.any(Post.class));

        updatePost.setContent(content);
        postService.update(updatePost);
        Mockito.verify(postService, Mockito.times(2)).update(Mockito.any(Post.class));

    }
}