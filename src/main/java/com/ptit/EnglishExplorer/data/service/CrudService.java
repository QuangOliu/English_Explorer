package com.ptit.EnglishExplorer.data.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<E, ID> {
    E save(E entity);

    List<E> findAll();

    Optional<E> findById(ID id);

    E update(ID id, E entity);

    void deleteById(ID id);

    boolean existsById(ID id);
}
