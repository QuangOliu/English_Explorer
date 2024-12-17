package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.dto.ActionRequestDto;
import com.ptit.EnglishExplorer.data.dto.ActionResponDto;
import com.ptit.EnglishExplorer.data.entity.Action;
import com.ptit.EnglishExplorer.data.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/actions")
public class ActionController extends BaseController<Action, Long, ActionService> {
    @Autowired
    public ActionController(ActionService service) {
        super(service);
    }

    // Read all entities
    @GetMapping("/check-question/{id}")
    public ResponseEntity<ActionResponDto> getAll(@PathVariable Long id) {
        ActionResponDto entities = service.checkQuestion(id);
        return new ResponseEntity<ActionResponDto>(entities, HttpStatus.OK);
    }

    @PostMapping("/toggle")
    public ResponseEntity<ActionResponDto> toggleAction(@RequestBody ActionRequestDto request) {
        ActionResponDto updatedState = service.toggleAction(request.getQuestion(), request.getActionType());
        return new ResponseEntity<>(updatedState, HttpStatus.OK);
    }

}
