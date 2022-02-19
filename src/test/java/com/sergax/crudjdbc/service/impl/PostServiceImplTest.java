package com.sergax.crudjdbc.service.impl;

import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.model.PostStatus;
import com.sergax.crudjdbc.model.Tag;
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
        Post post = postService.getById(5L);

        assertNotNull(post);
        assertEquals(5L, post.getPost_id());
        Mockito.verify(postService, Mockito.times(1)).getById(Mockito.anyLong());
    }

    @Test
    void delete() {
        Post createPost = new Post(2L, "Content2", null, null);
        postService.delete(createPost.getPost_id());
        Post find = postService.getById(2L);

        assertNull(find);
        Mockito.verify(postService, Mockito.times(1)).delete(Mockito.anyLong());
    }

    @Test
    void get_all_Post() {
        List<Post> posts = postService.getAll();
        Long count = posts.stream().count();

        assertNotNull(posts);
        assertEquals(posts.size(), count);
        Mockito.verify(postService, Mockito.times(1)).getAll();
    }

    @Test
    void create() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag(null, "Tag2"));
        Post newPost = new Post(null, "Content", tagList, PostStatus.ACTIVE);

        assertNotNull(newPost);
        assertNotNull(newPost.getPost_id());
        assertEquals("Content", newPost.getContent());
        Mockito.verify(postService, Mockito.times(1)).create(Mockito.any(Post.class));
    }

    @Test
    void update() {
        Post updatePost = postService.getById(3L);
        Post updatedPost = new Post();
        updatedPost.setPost_id(updatePost.getPost_id());
        updatedPost.setContent("Content");
        updatedPost = postService.update(updatedPost);

        assertNotNull(updatedPost);
        assertEquals(3L, updatedPost.getPost_id());
        assertEquals("Content", updatedPost.getContent());
        Mockito.verify(postService, Mockito.times(1)).update(Mockito.any(Post.class));
    }
}