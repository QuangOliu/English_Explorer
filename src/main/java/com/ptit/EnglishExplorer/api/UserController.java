package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.repository.UserRepository;
import com.ptit.EnglishExplorer.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController extends BaseController<User, Long, UserService>{
    @Autowired
    public UserController(UserService service) {
        super(service);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        User user = ApplicationAuditAware.getCurrentUser();
        return ResponseEntity.ok(user);
    }
}
