package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Role;
import com.ptit.EnglishExplorer.data.service.CrudService;
import com.ptit.EnglishExplorer.data.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/roles")
public class RoleController extends BaseController<Role, Long, RoleService> {
    @Autowired
    public RoleController(RoleService service) {
        super(service);
    }
}
