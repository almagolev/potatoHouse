package com.alma.potatoHouse.controller;

import com.alma.potatoHouse.entities.*;
import com.alma.potatoHouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alma.potatoHouse.Assist.*;

@RestController
@RequestMapping("/api")
public class ClientPartController {

    @Autowired
    private ActionsRepository actionsRepository;
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        ArrayList<Client> clients=new ArrayList(clientRepository.findAll());
        if (!clients.isEmpty())
            return new ResponseEntity(clients, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") Integer clientId) {
        try {
            Optional<Client> client = clientRepository.findById(clientId);
            return ResponseEntity.ok().body(client.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/clients-active/{is_active}")
    public ResponseEntity<Client> getAllClientsByStatus(@PathVariable(value = "is_active") boolean is_active) {
        ArrayList<Client> clients=new ArrayList(clientRepository.getAllClientsByStatus(is_active));
        if (!clients.isEmpty())
            return new ResponseEntity(clients, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clients-active-gender/{is_active}/{gender}")
    public ResponseEntity<Client> getAllClientsByStatus(@PathVariable(value = "is_active") boolean is_active,
                                                        @PathVariable(value = "gender") String gender) {
        ArrayList<Client> clients=new ArrayList(clientRepository.getAllClientsByStatus(is_active,gender));
        if (!clients.isEmpty())
            return new ResponseEntity(clients, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clients-b/{birthday}")
    public ResponseEntity<Client> getAllClientsBirthday(@PathVariable(value = "birthday") String birthday) {
        ArrayList<Client> clients=new ArrayList(clientRepository.getAllClientsBirthday(birthday));
        if (!clients.isEmpty())
            return new ResponseEntity(clients, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clients-gender/{gender}")
    public ResponseEntity<Client> getAllClientsByGender(@PathVariable(value = "gender") String gender) {
        ArrayList<Client> clients=new ArrayList(clientRepository.getAllClientsByGender(gender));
        if (!clients.isEmpty())
            return new ResponseEntity(clients, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clients-date/{join_date}")
    public ResponseEntity<Client> getAllClientsByDate(@PathVariable(value = "join_date") LocalDate join_date) {
        ArrayList<Client> clients=new ArrayList(clientRepository.getAllClientsByDate(join_date));
        if (!clients.isEmpty())
            return new ResponseEntity(clients, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clients-part/{s_id}")
    public ResponseEntity<List<Client>> getAllAvailableClients(@PathVariable(value = "s_id") int s_id) {
        ArrayList<Client> clients=new ArrayList(clientRepository.getAllAvailableClients(s_id));
        if (!clients.isEmpty())
            return new ResponseEntity(clients, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        Integer cId = client.getId();
        if (clientRepository.existsById(cId))
            updateClient(cId, client);
        try {
            clientRepository.save(client);
            Actions actions = new Actions(client.getId(), CLIENT, 0,"",JOIN,
                    String.format("%s %s %s HAS JOINED POTATO HOUSE!", CLIENT, client.getFirst_name(),client.getLast_name()),
                    LocalDateTime.now());
            actionsRepository.save(actions);
            return new ResponseEntity<>(client,HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/clients/{id}")
    public ResponseEntity updateClient(
            @PathVariable(value = "id") Integer clientId, @RequestBody Client clientDetails) {
        try {
            Client client = clientRepository.findById(clientId).get();
            List<String> changes = client.updateClient(clientDetails);
            Client updatedClient = clientRepository.save(clientDetails);
            for (String change : changes)
                actionsRepository.save(new Actions(clientId, CLIENT,0,"", UPDATE, change, LocalDateTime.now()));
            if(!clientDetails.getIs_active())
                act(client);
            return ResponseEntity.ok(updatedClient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/clients-b")
    public ResponseEntity updateBirthdayWish(@RequestBody Client clientDetails) {
        try {
            clientRepository.save(clientDetails);
            actionsRepository.save(new Actions(clientDetails.getId(), CLIENT,0,"", UPDATE,
                    String.format("A BIRTHDAY WISH WAS SENT TO %s %s!", clientDetails.getFirst_name(), clientDetails.getLast_name()),
                    LocalDateTime.now()));
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    private void act(Client client){
        try{
            List<Part> parts=partRepository.getPartByCIDFuture(client.getId(), String.valueOf(LocalDate.now()));
            if(parts!=null) {
                for (int i = 0; i < parts.size(); i++)
                    deletePartById(parts.get(i).getId());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/part/{s_id}")
    public ResponseEntity<Part> getPartBySID(@PathVariable(value = "s_id") int s_id) {
        try {
            ArrayList<Part> parts = new ArrayList(partRepository.getPartBySID(s_id));
            if (!parts.isEmpty())
                return new ResponseEntity(parts, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/part")
    public ResponseEntity insertPart(@RequestBody Part part) {
        try {
            partRepository.save(part);
            String msg=String.format("%s %s HAS SIGNED TO %s, %s:%s!",
                    CLIENT, part.getC_first() + " " + part.getC_last(), part.getW_title(), part.getW_date(), part.getW_start() + "-" + part.getW_end());
            signed(part,1);
            act(part,msg,SIGN);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/part-u")
    public ResponseEntity updatePart(@RequestBody Part part) {
        try {
            String msg;
            partRepository.save(part);
            if (part.getC_came().contains("N")) {
                msg = String.format("%s %s HAS UN-ATTENDED TO %s, %s:%s!",
                        CLIENT, part.getC_first() + " " + part.getC_last(), part.getW_title(), part.getW_date(), part.getW_start() + "-" + part.getW_end());
                done(part, -1);
//                canceled(part,1);
//                String msg =String.format("%s %s HAS CANCELED %s FROM %s %s-%s!",
//                        CLIENT,part.getC_first()+" "+part.getC_last(),
//                        part.getW_title(),part.getW_date(), part.getW_start(),part.getW_end());
//                act(part,msg,CANCEL);
            }
            else {
                msg = String.format("%s %s HAS ATTENDED TO %s, %s:%s!",
                        CLIENT, part.getC_first() + " " + part.getC_last(), part.getW_title(), part.getW_date(), part.getW_start() + "-" + part.getW_end());
                done(part,1);
//                canceled(part,-1);
            }
            act(part, msg, ATTENDED);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/part/{id}")
    public ResponseEntity deletePartById(@PathVariable(value = "id") int id) {
        try {
            Optional<Part> part = partRepository.findById(id);
            String msg=String.format("%s %s HAS CANCELED %s FROM %s %s-%s!",
                    CLIENT,part.get().getC_first()+" "+part.get().getC_last(),part.get().getW_title(),part.get().getW_date(), part.get().getW_start(),part.get().getW_end());
            signed(part.get(),-1);
            act(part.get(),msg,CANCEL);
            partRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    private void canceled(Part part, int up){
        Optional<Client> client = clientRepository.findById(part.getC_id());
        client.get().setW_canceled(client.get().getW_canceled()+up);
        clientRepository.save(client.get());
        Optional<Schedule> schedule = scheduleRepository.findById(part.getS_id());
        schedule.get().setW_canceled(schedule.get().getW_canceled()+up);
        scheduleRepository.save(schedule.get());
    }

    private void signed(Part part, int up){
        Optional<Client> client = clientRepository.findById(part.getC_id());
        client.get().setW_sign(client.get().getW_sign()+up);
        clientRepository.save(client.get());
        Optional<Schedule> schedule = scheduleRepository.findById(part.getS_id());
        schedule.get().setW_signed(schedule.get().getW_signed()+up);
        scheduleRepository.save(schedule.get());
    }

    private void done(Part part, int up){
        Optional<Client> client = clientRepository.findById(part.getC_id());
        client.get().setW_done(client.get().getW_done()+up);
        clientRepository.save(client.get());
        Optional<Schedule> schedule = scheduleRepository.findById(part.getS_id());
        schedule.get().setW_done(schedule.get().getW_done()+up);
        scheduleRepository.save(schedule.get());
    }

    private void act(Part part, String msg, String type){
        actionsRepository.save(new Actions
                (part.getC_id(), CLIENT, part.getW_id(), WORKOUT, type,msg, LocalDateTime.now()));
    }
}