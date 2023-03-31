package com.gbsfo.test.task.service;

import java.util.List;

public interface DefaultService<T> {

    T getById(Long id);

    List<T> getAll();

    T update(T t);

    void delete(Long id);
}
