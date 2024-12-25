package com.ptit.EnglishExplorer.data.entity;

import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_user", indexes = {@Index(columnList = "username", unique = true)})
@AttributeOverrides({
        @AttributeOverride(name = "create_date", column = @Column(nullable = true)),
        @AttributeOverride(name = "created_by", column = @Column(nullable = true))
})
public class User extends AuditableEntity implements UserDetails {

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

    @Column(name = "just_created")
    private Boolean justCreated;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "fullname", length = 100, nullable = false, updatable = false)
    private String fullname;

    @Column(name = "photo", columnDefinition = "LONGBLOB NULL")
    @Basic(fetch = FetchType.EAGER)
    private String photo;

    @Column(name = "photo_cropped")
    private Boolean photoCropped;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    /* Spring Security fields */

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "account_non_expired")
    private Boolean accountNonExpired = true;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired != null ? accountNonExpired : true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked != null ? accountNonLocked : true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired != null ? credentialsNonExpired : true;
    }

    @Override
    public boolean isEnabled() {
        return active != null && active;
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) return true;
        if (rhs == null || getClass() != rhs.getClass()) return false;
        User user = (User) rhs;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
