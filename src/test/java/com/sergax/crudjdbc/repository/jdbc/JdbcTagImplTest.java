package com.sergax.crudjdbc.repository.jdbc;

import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.repository.TagRepository;
import com.sergax.crudjdbc.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JdbcTagImplTest {
    TagServiceImpl tagService = new TagServiceImpl();

    @Mock
    private Tag tag;

    @Mock
    private List<Tag> tagListMock;

    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById() {
        when(tagRepository.getById(anyLong())).thenReturn(tag);
        when(tag.getTag_id()).thenReturn(1L);
        when(tag.getName()).thenReturn("TestName");

        Tag tagTest = new Tag(1L, "TestName");
        assertEquals(tagTest.getTag_id(), tagService.getById(1L).getTag_id());
    }

    @Test
    void deleteById() {
        doNothing().when(tagRepository).deleteById(anyLong());
        tagService.delete(1L);
        verify(tagRepository).deleteById(1L);
    }

    @Test
    void update() {
        when(tagRepository.update(any())).thenReturn(tag);
        when(tag.getTag_id()).thenReturn(1L);

        Tag tagTest = new Tag(1L, "TagTest");
        assertEquals(tagTest.getTag_id(), tagRepository.update(tag).getTag_id());
    }

    @Test
    void create() {
        when(tagRepository.create(any())).thenReturn(tag);
        when(tag.getTag_id()).thenReturn(1L);

        Tag tagTest = new Tag(1L, "TagTest");
        assertEquals(tagTest.getTag_id(), tagService.create(tag).getTag_id());
    }

    @Test
    void getAll() {
        when(tagRepository.getAll()).thenReturn(tagListMock);
        when(tagListMock.size()).thenReturn(1);

        tagService.getAll();
        verify(tagRepository).getAll();

        tagListMock.size();
        verify(tagListMock).size();
    }
}