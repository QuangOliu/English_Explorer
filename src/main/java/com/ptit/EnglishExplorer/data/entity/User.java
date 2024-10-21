package com.ptit.EnglishExplorer.data.entity;

import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "tbl_user", indexes = { @Index(columnList = "username", unique = true) })
@AttributeOverrides({ @AttributeOverride(name = "create_date", column = @Column(nullable = true)),
        @AttributeOverride(name = "created_by", column = @Column(nullable = true)) })
public class User extends AuditableEntity {

    @Transient
    private static final long serialVersionUID = 5467396512939915793L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @Column(name = "just_created", nullable = false)
    private Boolean justCreated;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "fullname", length = 100, nullable = false)
    private String fullname;

    @Column(name = "photo", nullable = true, columnDefinition = "LONGBLOB NULL")
    @Basic(fetch = FetchType.EAGER)
    private byte[] photo;

    @Column(name = "photo_cropped", nullable = false)
    private Boolean photoCropped;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = true)
    private User manager;

    /* Spring Security fields */

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "account_non_expired", nullable = true)
    private Boolean accountNonExpired = true;

    @Column(name = "account_non_locked", nullable = true)
    private Boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", nullable = true)
    private Boolean credentialsNonExpired = true;

    // --------------------------------------
    // GETTERS/SETTERS
    // --------------------------------------

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof User) {
            return username.equals(((User) rhs).username);
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }

}