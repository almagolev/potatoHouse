package com.alma.potatoHouse.controller;

import com.alma.potatoHouse.entities.*;
import com.alma.potatoHouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ActionController {

    @Autowired
    private ActionsRepository actionsRepository;

    @GetMapping("/actions")
    public ResponseEntity<List<Actions>> getAllActions(){
        ArrayList<Actions> actions=new ArrayList(actionsRepository.findAll());
        if (!actions.isEmpty())
            return new ResponseEntity(actions, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/actions-by/{sub_do}/{type}")
    public ResponseEntity<List<Actions>> getAllActionsByChoice(@PathVariable String sub_do, @PathVariable String type){
        ArrayList<Actions> actions=new ArrayList(actionsRepository.getAllActionsByChoice(sub_do,type));
        if (!actions.isEmpty())
            return new ResponseEntity(actions, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}