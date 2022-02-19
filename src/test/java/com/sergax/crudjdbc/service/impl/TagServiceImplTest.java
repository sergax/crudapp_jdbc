package com.sergax.crudjdbc.service.impl;

import com.sergax.crudjdbc.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Spy
    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void get_by_id() {
        Tag tag = tagService.getById(2L);

        assertNotNull(tag);
        assertEquals(2L, tag.getTag_id());
        Mockito.verify(tagService, Mockito.times(1)).getById(Mockito.anyLong());
    }

    @Test
    void delete_by_id() {
        Tag tag = new Tag(1L, "Tag1");
        tagService.delete(tag.getTag_id());
        Tag find = tagService.getById(1L);

        assertNull(find);
        Mockito.verify(tagService, Mockito.times(1)).delete(Mockito.anyLong());
    }

    @Test
    void get_all() {
        List<Tag> tags = tagService.getAll();
        Long count = tags.stream().count();

        assertNotNull(tags);
        assertEquals(tags.size(), count);
        Mockito.verify(tagService, Mockito.times(1)).getAll();
    }

    @Test
    void create_tag() {
        Tag createTag = new Tag(1L, "Tag2");
        createTag = tagService.create(createTag);

        assertNotNull(createTag.getTag_id());
        assertEquals("Tag2", createTag.getName());
        Mockito.verify(tagService, Mockito.times(1)).create(Mockito.any(Tag.class));
    }

    @Test
    void update_tag() {
        Tag updateTag = tagService.getById(2L);
        String name1 = updateTag.getName();
        String name2 = "Tag2";
        updateTag.setName(name2);
        updateTag = tagService.update(updateTag);

        assertNotNull(updateTag);
        assertEquals(2L, updateTag.getTag_id());
        assertEquals("Tag2", updateTag.getName());
        Mockito.verify(tagService, Mockito.times(1)).update(Mockito.any(Tag.class));

        updateTag.setName(name1);
        tagService.update(updateTag);
        Mockito.verify(tagService, Mockito.times(2)).update(Mockito.any(Tag.class));
    }
}