package com.alma.potatoHouse.repository;

import com.alma.potatoHouse.entities.Actions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActionsRepository extends JpaRepository<Actions,Integer> {
    @Query(value = "SELECT * FROM actions WHERE (sub_do = ?1 or sub_on =?1) and type like ?2", nativeQuery = true)
    List<Actions> getAllActionsByChoice(String sub_do, String type);
//    @Query(value = "SELECT * FROM actions WHERE sub_do = ?1 and sub_on = ?2 and type like ?3", nativeQuery = true)
//    List<Actions> getAllActionsByChoice(String sub_do, String sub_on, String type);
}
