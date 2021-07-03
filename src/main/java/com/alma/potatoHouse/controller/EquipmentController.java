package com.alma.potatoHouse.controller;

import com.alma.potatoHouse.entities.Actions;
import com.alma.potatoHouse.entities.Equipment;
import com.alma.potatoHouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.alma.potatoHouse.Assist.*;

@RestController
@RequestMapping("/api")
public class EquipmentController {

    @Autowired
    private ActionsRepository actionsRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;

    @GetMapping("/equipments")
    public ResponseEntity<List<Equipment>> getAllEquipments() {
        ArrayList<Equipment> equipments=new ArrayList(equipmentRepository.findAll());
        if (!equipments.isEmpty())
            return new ResponseEntity(equipments, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/equipments")
    public ResponseEntity createEquipment(@RequestBody Equipment equipment){
        Integer eId=equipment.getId();
        if(!equipmentRepository.existsById(eId)) {
            equipmentRepository.save(equipment);
            Actions actions = new Actions(equipment.getId(), EQUIPMENT, 0,"",ADD,
                    String.format("%s %s HAS ADDED!", EQUIPMENT,equipment.getTitle()), LocalDateTime.now());
            actionsRepository.save(actions);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}