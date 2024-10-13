package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.repository.UserRepository;
import com.ptit.EnglishExplorer.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController extends BaseController<User, Long, UserService>{
    @Autowired
    public UserController(UserService service) {
        super(service);
    }
}
