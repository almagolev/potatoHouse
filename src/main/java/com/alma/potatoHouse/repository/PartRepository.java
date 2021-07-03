package com.alma.potatoHouse.repository;

import com.alma.potatoHouse.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.xml.crypto.Data;
import java.util.List;

public interface PartRepository extends JpaRepository<Part, Integer> {
    @Query(value = "SELECT * FROM part WHERE s_id = ?", nativeQuery = true)
    List<Part> getPartBySID(int s_id);

    @Query(value = "SELECT * FROM part WHERE c_id = ?1 and w_date >?2", nativeQuery = true)
    List<Part> getPartByCIDFuture(int c_id, String date);
}