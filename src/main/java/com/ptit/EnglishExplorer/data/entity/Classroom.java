package com.ptit.EnglishExplorer.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import com.ptit.EnglishExplorer.data.types.AccessTypeCustom;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Data
@Table(name = "tbl_classroom")
public class Classroom extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    private AccessTypeCustom accessType;

    @NotNull
    private String name;

    private long cost = 0;

    private String description;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)  // Corrected to "lesson"
    @JsonManagedReference
    Set<Exam> exams = new HashSet<>();

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JsonManagedReference
    Set<ClassMember> classMembers= new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = true)
    User user;

    @ManyToMany(mappedBy = "classrooms")
    private Set<Course> courses = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return Objects.equals(id, classroom.id) && Objects.equals(name, classroom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", accessType=" + accessType +
                '}';
    }
}
