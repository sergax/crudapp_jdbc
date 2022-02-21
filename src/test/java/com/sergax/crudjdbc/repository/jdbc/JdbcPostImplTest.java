package com.sergax.crudjdbc.repository.jdbc;

import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.model.PostStatus;
import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.repository.PostRepository;
import com.sergax.crudjdbc.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JdbcPostImplTest {

    @InjectMocks
    PostServiceImpl postService = new PostServiceImpl();

    @Mock
    private Post post;

    @Mock
    private List<Post> postListMock;

    @Mock
    private List<Tag> tagListMock;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById() {
        when(postRepository.getById(anyLong())).thenReturn(post);
        when(post.getPost_id()).thenReturn(1L);

        Post postTest = new Post(1L, "Content", tagListMock, PostStatus.ACTIVE);
        assertEquals(postTest.getPost_id(), postService.getById(1L).getPost_id());
    }

    @Test
    void deleteById() {
        doNothing().when(postRepository).deleteById(anyLong());
        postService.delete(1L);
        verify(postRepository).deleteById(1L);
    }

    @Test
    void update() {
        when(postRepository.update(any())).thenReturn(post);
        when(post.getPost_id()).thenReturn(1L);

        Post postTest = new Post(1L, "ContentTest", tagListMock, PostStatus.ACTIVE);
        assertEquals(postTest.getPost_id(), postService.update(any()).getPost_id());
    }

    @Test
    void create() {
        when(postRepository.create(any())).thenReturn(post);
        when(post.getPost_id()).thenReturn(1L);

        Post postTest = new Post(1L, "ContentTest", tagListMock, PostStatus.ACTIVE);
        assertEquals(postTest.getPost_id(), postService.create(any()).getPost_id());
    }

    @Test
    void getAll() {
        when(postRepository.getAll()).thenReturn(postListMock);
        when(postListMock.size()).thenReturn(1);

        postService.getAll();
        verify(postRepository).getAll();

        postListMock.size();
        verify(postListMock).size();
    }
}