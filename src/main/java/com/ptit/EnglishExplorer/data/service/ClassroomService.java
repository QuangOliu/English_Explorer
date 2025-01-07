package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.dto.ClassroomDTO;
import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.vnpay.ResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassroomService extends CrudService<Classroom, Long> {
    Page<ClassroomDTO> findListClassroomDTO(Pageable pageable);

    ResponseObject<?> buyById(Long id);

    Page<Classroom> findMyOwnClassroom(Pageable pageable);
}
