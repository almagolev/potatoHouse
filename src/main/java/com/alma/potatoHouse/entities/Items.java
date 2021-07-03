package com.alma.potatoHouse.entities;

import java.time.LocalDateTime;
import java.util.Date;

public class Items {
  private int id, price;
  private LocalDateTime added_on;
  private String title;
  private boolean active;

  public Items(int price, String title) {
    this.price = price;
    this.title = title;
    this.active = true;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public LocalDateTime getAdded_on() {
    return added_on;
  }

  public String getTitle() {
    return title;
  }

//  public void setTitle(String title) {
//    this.title = title;
//  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
