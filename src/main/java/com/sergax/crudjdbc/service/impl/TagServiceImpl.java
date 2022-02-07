package com.sergax.crudjdbc.service.impl;

import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.repository.TagRepository;
import com.sergax.crudjdbc.repository.jdbc.JdbcTagImpl;
import com.sergax.crudjdbc.service.TagService;

import java.util.List;

public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository = new JdbcTagImpl();

    @Override
    public Tag getById(Long id) {
        return tagRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List getAll() {
        return tagRepository.getAll();
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag update(Tag tag) {
        return tagRepository.update(tag);
    }
}

