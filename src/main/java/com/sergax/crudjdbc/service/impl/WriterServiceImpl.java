package com.sergax.crudjdbc.service.impl;

import com.sergax.crudjdbc.model.Writer;
import com.sergax.crudjdbc.repository.WriterRepository;
import com.sergax.crudjdbc.repository.jdbc.JdbcWriterImpl;
import com.sergax.crudjdbc.service.WriterService;

import java.util.List;

public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository = new JdbcWriterImpl();

    public Writer getById(Long id) {
        return writerRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        writerRepository.deleteById(id);
    }

    @Override
    public List getAll() {
        return writerRepository.getAll();
    }

    @Override
    public Writer create(Writer writer) {
        return writerRepository.create(writer);
    }

    @Override
    public Writer update(Writer writer) {
        return writerRepository.update(writer);
    }
}

