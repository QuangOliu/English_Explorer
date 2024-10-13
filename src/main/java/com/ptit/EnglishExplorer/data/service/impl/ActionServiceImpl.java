package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Action;
import com.ptit.EnglishExplorer.data.entity.Role;
import com.ptit.EnglishExplorer.data.repository.ActionRepository;
import com.ptit.EnglishExplorer.data.repository.RoleRepository;
import com.ptit.EnglishExplorer.data.service.ActionService;
import com.ptit.EnglishExplorer.data.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl extends BaseServiceImpl<Action, Long, ActionRepository> implements ActionService {

    @Autowired
    public ActionServiceImpl(ActionRepository repository) {
        super(repository);
    }

}
