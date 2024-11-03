package com.ptit.EnglishExplorer.auth;
import com.ptit.EnglishExplorer.data.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username; // Thêm trường username
    private String fullname; // Thêm trường fullname
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>(); // Giữ nguyên trường roles

}