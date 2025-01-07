package com.ptit.EnglishExplorer;

import com.ptit.EnglishExplorer.data.entity.Role;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.repository.RoleRepository;
import com.ptit.EnglishExplorer.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Tạo các Role nếu chưa có
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER");
        createRoleIfNotExists("TEACHER");

        // Tạo User nếu chưa có
        createUserIfNotExists("admin", "admin", "ADMIN");
        createUserIfNotExists("teacher", "teacher", "TEACHER");
        createUserIfNotExists("user", "user", "USER");
    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName) == null) {
            roleRepository.save(new Role(roleName));
        }
    }

    private void createUserIfNotExists(String username, String password, String roleName) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));
                user.setEmail(username + "@gmail.com");
                user.setFullname(username);
                user.setActive(true);
                user.getRoles().add(role);
                userRepository.save(user);
            } else {
                throw new IllegalStateException("Role '" + roleName + "' không tồn tại trong database.");
            }
        }
    }
}
