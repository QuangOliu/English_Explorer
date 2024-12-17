package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.dto.ActionResponDto;
import com.ptit.EnglishExplorer.data.entity.Action;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.types.ActionType;

public interface ActionService extends CrudService<Action, Long> {
    public ActionResponDto checkQuestion(Long questionId);
    public ActionResponDto toggleAction(Question question, ActionType actionType);
}
