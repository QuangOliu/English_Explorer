package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.dto.ActionResponDto;
import com.ptit.EnglishExplorer.data.entity.Action;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.entity.Role;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.repository.ActionRepository;
import com.ptit.EnglishExplorer.data.repository.RoleRepository;
import com.ptit.EnglishExplorer.data.service.ActionService;
import com.ptit.EnglishExplorer.data.service.RoleService;
import com.ptit.EnglishExplorer.data.types.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionServiceImpl extends BaseServiceImpl<Action, Long, ActionRepository> implements ActionService {

    @Autowired
    public ActionServiceImpl(ActionRepository repository) {
        super(repository);
    }

    @Override
    public ActionResponDto checkQuestion(Long questionId) {
        User user = ApplicationAuditAware.getCurrentUser();
        // Initialize response DTO with default values
        ActionResponDto response = new ActionResponDto(false, false, false);

        // Fetch all actions for the given user and question
        List<Action> actions = repository.findByUserIdAndQuestionId(user.getId(), questionId);

        // Iterate through actions and set response fields based on action type
        for (Action action : actions) {
            if (action.getActionType() == ActionType.LOVE) {
                response.setLove(true);
            } else if (action.getActionType() == ActionType.SHARE) {
                response.setShare(true);
            } else if (action.getActionType() == ActionType.BOOKMARK) {
                response.setBookMark(true);
            }
        }

        return response;
    }

    @Override
    public ActionResponDto toggleAction(Question question, ActionType actionType) {
        User user = ApplicationAuditAware.getCurrentUser();

        // Check if the action already exists
        Optional<Action> existingAction = repository.findByUserIdAndQuestionIdAndActionType(user.getId(), question.getId(), actionType);

        if (existingAction.isPresent()) {
            // If the action exists, delete it (toggle off)
            repository.delete(existingAction.get());
        } else {
            // If it doesn’t exist, create a new action (toggle on)
            Action action = new Action();
            action.setUser(user);
            action.setQuestion(question);
            action.setActionType(actionType);
            repository.save(action);
        }

        // Return the updated state after toggling
        return checkQuestion(question.getId());
    }

}
