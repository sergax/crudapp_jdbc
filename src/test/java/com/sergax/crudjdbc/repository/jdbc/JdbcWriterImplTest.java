package com.sergax.crudjdbc.repository.jdbc;

import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.model.Writer;
import com.sergax.crudjdbc.repository.WriterRepository;
import com.sergax.crudjdbc.service.impl.WriterServiceImpl;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JdbcWriterImplTest {

    @InjectMocks
    WriterServiceImpl writerService = new WriterServiceImpl();

    @Mock
    private Writer writer;

    @Mock
    private List<Post> postListMock;

    @Mock
    private List<Writer> writerListMock;

    @Mock
    private WriterRepository writerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById() {
        when(writerRepository.getById(anyLong())).thenReturn(writer);
        when(writer.getWriter_id()).thenReturn(1L);

        Writer writerTest = new Writer(1L, "NameTest", postListMock);
        assertEquals(writerTest.getWriter_id(), writerService.getById(anyLong()).getWriter_id());
    }

    @Test
    void deleteById() {
        doNothing().when(writerRepository).deleteById(anyLong());
        writerService.delete(1L);
        verify(writerRepository).deleteById(1L);
    }

    @Test
    void update() {
        when(writerRepository.update(any())).thenReturn(writer);
        when(writer.getWriter_id()).thenReturn(1L);

        Writer writerTest = new Writer(1L, "WriterTest", postListMock);
        assertEquals(writerTest.getWriter_id(), writerRepository.update(writer).getWriter_id());
    }

    @Test
    void create() {
        when(writerRepository.create(any())).thenReturn(writer);
        when(writer.getWriter_id()).thenReturn(1L);

        Writer writerTest = new Writer(1L, "WriterTest", postListMock);
        assertEquals(writerTest.getWriter_id(), writerService.create(writer).getWriter_id());
    }

    @Test
    void getAll() {
        when(writerRepository.getAll()).thenReturn(writerListMock);
        when(writerListMock.size()).thenReturn(1);

        writerService.getAll();
        verify(writerRepository).getAll();

        writerListMock.size();
        verify(writerListMock).size();
    }
}