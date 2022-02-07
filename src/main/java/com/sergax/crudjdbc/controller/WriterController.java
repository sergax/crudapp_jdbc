package com.sergax.crudjdbc.controller;

import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.model.Writer;
import com.sergax.crudjdbc.repository.WriterRepository;
import com.sergax.crudjdbc.service.PostService;
import com.sergax.crudjdbc.service.WriterService;
import com.sergax.crudjdbc.service.impl.PostServiceImpl;
import com.sergax.crudjdbc.service.impl.WriterServiceImpl;

import java.util.List;

public class WriterController {
    private final WriterService writerService = new WriterServiceImpl();

    public List<Writer> getAll() {
        return writerService.getAll();
    }

    public Writer getById(Long id) {
        return writerService.getById(id);
    }

    public Writer save(Writer writer) {
        return writerService.save(writer);
    }

    public Writer update(Writer writer) {
        return writerService.update(writer);
    }

    public void deleteById(Long id) {
        writerService.delete(id);
    }
}
