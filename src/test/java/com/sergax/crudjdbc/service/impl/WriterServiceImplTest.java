package com.sergax.crudjdbc.service.impl;

import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.model.PostStatus;
import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.model.Writer;
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
class WriterServiceImplTest {

    @Spy
    @InjectMocks
    private WriterServiceImpl writerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void get_by_id() {
        Writer writer = writerService.getById(4L);

        assertNotNull(writer);
        assertEquals(4L, writer.getWriter_id());
        Mockito.verify(writerService, Mockito.times(1)).getById(Mockito.anyLong());
    }

    @Test
    void delete() {
        Writer createWriter = new Writer(9L, "Name", null);
        writerService.delete(createWriter.getWriter_id());
        Writer find = writerService.getById(2L);

        assertNull(find);
        Mockito.verify(writerService, Mockito.times(1)).delete(Mockito.anyLong());
    }

    @Test
    void getAll() {
        List<Writer> writers = writerService.getAll();
        Long count = writers.stream().count();

        assertNotNull(writers);
        assertEquals(writers.size(), count);
        Mockito.verify(writerService, Mockito.times(1)).getAll();
    }

    @Test
    void create() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag(null, "Tag"));
        List<Post> postList = new ArrayList<>();
        postList.add(new Post(null, "Post", tagList, PostStatus.ACTIVE));
        Writer newWriter = new Writer(null, "Name", postList);

        assertNotNull(newWriter);
        assertNotNull(newWriter.getWriter_id());
        assertEquals("Name", newWriter.getName());
        Mockito.verify(writerService, Mockito.times(1)).create(Mockito.any(Writer.class));
    }

    @Test
    void update() {
        Writer updateWriter = writerService.getById(4L);
        Writer updatedWriter = new Writer();
        updatedWriter.setWriter_id(updateWriter.getWriter_id());
        updatedWriter.setName("Name");
        updatedWriter = writerService.update(updatedWriter);

        assertNotNull(updatedWriter);
        assertEquals(4L, updatedWriter.getWriter_id());
        assertEquals("Name", updatedWriter.getName());
        Mockito.verify(writerService, Mockito.times(1)).update(Mockito.any(Writer.class));
    }
}