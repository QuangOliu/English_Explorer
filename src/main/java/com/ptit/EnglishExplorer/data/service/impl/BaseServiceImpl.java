package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<E, ID, R extends JpaRepository<E, ID>> implements CrudService<E, ID> {

    protected final R repository;

    @Autowired
    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public E update(ID id, E entity) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Entity with ID " + id + " not found");
        }
        return repository.save(entity);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public Page<E> findList(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
