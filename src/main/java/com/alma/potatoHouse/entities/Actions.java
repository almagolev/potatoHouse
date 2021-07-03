package com.alma.potatoHouse.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name= "actions")
@EntityListeners(AuditingEntityListener.class)
public class Actions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sub_do_id", updatable = false)
    private int sub_do_id;

    @Column(name = "sub_do", updatable = false)
    private String sub_do;

    @Column(name = "sub_on_id", updatable = false)
    private int sub_on_id;

    @Column(name = "sub_on", updatable = false)
    private String sub_on;

//    @Column(name = "subject_id", updatable = false)
//    private int subject_id;
//
//    @Column(name = "subject", updatable = false)
//    private String subject;

    @Column(name = "type", updatable = false)
    private String type;

    @Column(name = "info", updatable = false)
    private String info;

    @Column(name = "date", updatable = false)
    private LocalDateTime date;

    @PostUpdate
    public void postUpdate() {
        date = LocalDateTime.now();
    }

    public Actions(){}

    public Actions(int sub_do_id, String sub_do, int sub_on_id, String sub_on,String type, String info, LocalDateTime date) {
        this.sub_do_id = sub_do_id;
        this.sub_do = sub_do;
        this.sub_on_id = sub_on_id;
        this.sub_on = sub_on;
        this.type = type;
        this.info = info;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSub_do_id() {
        return sub_do_id;
    }

    public void setSub_do_id(int sub_do_id) {
        this.sub_do_id = sub_do_id;
    }

    public String getSub_do() {
        return sub_do;
    }

    public void setSub_do(String sub_do) {
        this.sub_do = sub_do;
    }

    public int getSub_on_id() {
        return sub_on_id;
    }

    public void setSub_on_id(int sub_on_id) {
        this.sub_on_id = sub_on_id;
    }

    public String getSub_on() {
        return sub_on;
    }

    public void setSub_on(String sub_on) {
        this.sub_on = sub_on;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
