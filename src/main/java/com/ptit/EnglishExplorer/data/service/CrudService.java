package com.ptit.EnglishExplorer.data.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrudService<E, ID> {
    E save(E entity);

    List<E> findAll();

    Optional<E> findById(ID id);

    E update(ID id, E entity);

    void deleteById(ID id);

    boolean existsById(ID id);

    // Thêm phương thức phân trang
    Page<E> findList(Pageable pageable);
}
