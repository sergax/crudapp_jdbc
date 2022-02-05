package com.sergax.crudjdbc.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T getId (ID id);

    void deleteBuID(ID id);

    T update(T item);

    T save(T item);

    List<T> getAll();
}
