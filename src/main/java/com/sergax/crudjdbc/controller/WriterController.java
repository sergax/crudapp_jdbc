package com.sergax.crudjdbc.controller;

import com.sergax.crudjdbc.model.Writer;
import com.sergax.crudjdbc.service.WriterService;
import com.sergax.crudjdbc.service.impl.WriterServiceImpl;

import java.util.List;

public class WriterController {
    WriterService writerService = new WriterServiceImpl();

    public List<Writer> getAll() {
        return writerService.getAll();
    }

    public Writer getById(Long id) {
        return writerService.getById(id);
    }

    public Writer create(Writer writer) {
        return writerService.create(writer);
    }

    public Writer update(Writer writer) {
        return writerService.update(writer);
    }

    public void deleteById(Long id) {
        writerService.delete(id);
    }
}
