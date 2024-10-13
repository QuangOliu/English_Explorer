package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService extends CrudService<User, Long> {


    public Optional<User> getUserByUsername(String username);

}
