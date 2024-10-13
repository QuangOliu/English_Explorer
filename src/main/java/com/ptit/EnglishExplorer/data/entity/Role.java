package com.ptit.EnglishExplorer.data.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "tbl_role", indexes = { @Index(columnList = "role_name", unique = true) })
public class Role extends AuditableEntity{

    @Transient
    private static final long serialVersionUID = 9092774599340607981L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "role_name", length = 100, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users = new HashSet<User>();

    // --------------------------------------
    // GrantedAuthority implementation
    // --------------------------------------

    public String getAuthority() {
        return this.name;
    }

    // --------------------------------------
    // GETTERS/SETTERS
    // --------------------------------------
    @Transient
    @Override
    public String toString() {
        return String.format("Role: %s", this.name);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Role)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Role that = (Role) obj;
        return obj == this.id || obj.equals(this.id);
    }

}
