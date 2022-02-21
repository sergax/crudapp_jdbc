package com.sergax.crudjdbc.controller;

import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.service.TagService;
import com.sergax.crudjdbc.service.impl.TagServiceImpl;

import java.util.List;

public class TagController {
    TagService tagService = new TagServiceImpl();

    public List<Tag> getAll() {
        return tagService.getAll();
    }

    public Tag getById(Long id) {
        return tagService.getById(id);
    }

    public Tag create(Tag tag) {
        return tagService.create(tag);
    }

    public Tag update(Tag tag) {
        return tagService.update(tag);
    }

    public void deleteById(Long id) {
        tagService.delete(id);
    }
}
