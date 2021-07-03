package com.alma.potatoHouse.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "clients")
@EntityListeners({AuditingEntityListener.class})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_client", updatable = false)
    private String id_client;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday", updatable = false)
    private String birthday;

    @Column(name = "home_address")
    private String home_address;

    @Column(name = "is_active")
    private Boolean is_active;

    @Column(name = "has_health")
    private Boolean has_health;

    @Column(name = "gender")
    private String gender;

    @Column(name = "w_sign")
    private int w_sign;

    @Column(name = "w_done")
    private int w_done;

    @Column(name = "w_canceled")
    private int w_canceled;

    @Column(name = "join_date", updatable = false)
    private LocalDate join_date;

    @Column(name = "last_b_wish")
    private String last_b_wish;

    @PrePersist
    public void prePersist() {
        join_date = LocalDate.now();
        last_b_wish = "1900-01-01";
        is_active = Boolean.TRUE;
        has_health= Boolean.TRUE;
        toLower();
    }

    public Client(){}

    public int getId() {
        return id;
    }

    public int getW_canceled() {
        return w_canceled;
    }

    public String getId_client() {
        return id_client;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getHome_address() {
        return home_address;
    }

    public String getBirthday() {
        return birthday;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Boolean has_health() {
        return has_health;
    }

    public void setHas_health(Boolean has_health) {
        this.has_health = has_health;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getJoin_date() {
        return join_date;
    }

    public void setJoin_date(LocalDate join_date) {
        this.join_date = join_date;
    }

    public String getLast_b_wish() {
        return last_b_wish;
    }

    public void setLast_b_wish(String last_b_wish) {
        this.last_b_wish = last_b_wish;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public Boolean getHas_health() {
        return has_health;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setW_canceled(int w_canceled) {
        this.w_canceled = w_canceled;
    }

    public int getW_sign() {
        return w_sign;
    }

    public void setW_sign(int w_sign) {
        this.w_sign = w_sign;
    }

    public int getW_done() {
        return w_done;
    }

    public void setW_done(int w_done) {
        this.w_done = w_done;
    }

    public List<String> updateClient(Client client_new) {
        client_new.toLower();
        List<String> changes=new ArrayList<>();

        if(is_active != client_new.is_active){
            is_active=client_new.is_active;
            addChange(changes,"ACTIVE", String.valueOf(!is_active),String.valueOf(is_active));
        }

        if(has_health != client_new.has_health){
            has_health=client_new.has_health;
            addChange(changes,"HEALTH", String.valueOf(!has_health),String.valueOf(has_health));
        }

        if(!first_name.equals(client_new.first_name)){
            addChange(changes,"FIRST NAME", first_name,client_new.first_name);
            first_name= client_new.first_name;
        }
        if(!last_name.equals(client_new.last_name)){
            addChange(changes,"LAST NAME", last_name,client_new.last_name);
            last_name= client_new.last_name;
        }
        if(!phone_number.equals(client_new.phone_number)){
            addChange(changes,"PHONE NUMBER", phone_number,client_new.phone_number);
            phone_number= client_new.phone_number;
        }
        if(!email.equals(client_new.email)){
            addChange(changes,"EMAIL", email,client_new.email);
            email= client_new.email;
        }
        if(!home_address.equals(client_new.home_address)){
            addChange(changes,"ADDRESS", home_address,client_new.home_address);
            home_address= client_new.home_address;
        }
        if(!last_b_wish.equals(client_new.last_b_wish)){
            changes.add("A BIRTHDAY WISH WAS SENT!");
            last_b_wish= client_new.last_b_wish;
        }

        return changes;
    }

    private void addChange(List<String> changes, String title, String was, String is){
        changes.add(String.format("%s: WAS %s, NOW %s",title,was,is));
    }

    public void toLower(){
        first_name=first_name.toLowerCase();
        last_name=last_name.toLowerCase();
        home_address=home_address.toLowerCase();
        email=email.toLowerCase();
    }
}