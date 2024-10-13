package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Action;
import com.ptit.EnglishExplorer.data.entity.Role;
import com.ptit.EnglishExplorer.data.service.ActionService;
import com.ptit.EnglishExplorer.data.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/actions")
public class ActionController extends BaseController<Action, Long, ActionService> {
    @Autowired
    public ActionController(ActionService service) {
        super(service);
    }
}
