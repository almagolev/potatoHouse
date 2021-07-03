package com.alma.potatoHouse.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

@Entity
@Table(name= "equipment")
@EntityListeners({AuditingEntityListener.class})
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", updatable = false, unique = true)
    String title;

    @Column(name = "amount")
    int amount;

    public Equipment(){}

    public Equipment(String title, int amount){
        this.title=title;
        this.amount=amount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}