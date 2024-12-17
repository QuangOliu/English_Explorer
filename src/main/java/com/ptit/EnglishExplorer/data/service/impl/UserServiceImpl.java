package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.repository.UserRepository;
import com.ptit.EnglishExplorer.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Object loadUserByUsername(String username) {
        return null;
    }

    @Override
    public User update(Long id, User entity) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Entity with ID " + id + " not found");
        }
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return repository.save(entity);
    }

}
