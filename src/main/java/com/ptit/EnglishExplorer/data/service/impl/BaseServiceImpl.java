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
    public Boolean deleteById(ID id) {
        if (repository.existsById(id)) {  // Kiểm tra xem thực thể có tồn tại trong cơ sở dữ liệu hay không
            repository.deleteById(id);    // Xóa thực thể nếu tồn tại
            return true;                   // Trả về true nếu xóa thành công
        }
        return false;                      // Trả về false nếu không tìm thấy thực thể
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
