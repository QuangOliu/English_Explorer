package com.ptit.EnglishExplorer.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_course")
public class Course extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

}
