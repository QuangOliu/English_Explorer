package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.service.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public abstract class BaseController<E, ID, S extends CrudService<E, ID>> {

    protected final S service;

    public BaseController(S service) {
        this.service = service;
    }

    // Create entity
    @PostMapping
    public ResponseEntity<E> create(@RequestBody E entity) {
        E createdEntity = service.save(entity);
        return new ResponseEntity<>(createdEntity, HttpStatus.CREATED);
    }

    // Read all entities
    @GetMapping
    public ResponseEntity<List<E>> getAll() {
        List<E> entities = service.findAll();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<E>> getAllPaged(
            @PageableDefault(size = 10, page = 0) Pageable pageable) { // Thiết lập kích thước mặc định
        Page<E> entities = service.findList(pageable);
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    // Read entity by ID
    @GetMapping("/{id}")
    public ResponseEntity<E> getById(@PathVariable ID id) {
        Optional<E> entity = service.findById(id);
        return entity.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update entity by ID
    @PutMapping("/{id}")
    public ResponseEntity<E> update(@PathVariable ID id, @RequestBody E updatedEntity) {
        if (!service.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        E entity = service.update(id, updatedEntity);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    // Delete entity by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<E> delete(@PathVariable ID id) {
        // Kiểm tra sự tồn tại của thực thể
        if (!service.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Lấy thực thể để có thể trả về sau khi xóa
        Optional<E> entity = service.findById(id);

        // Xóa thực thể
        service.deleteById(id);

        // Trả về thực thể đã bị xóa cùng với mã trạng thái OK
        return ResponseEntity.ok(entity.get());
    }

}
