package com.alma.potatoHouse.repository;

import com.alma.potatoHouse.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query(value = "SELECT * FROM Clients WHERE is_active = ?1", nativeQuery = true)
    List<Client> getAllClientsByStatus(boolean is_active);

    @Query(value = "SELECT * FROM Clients WHERE is_active = ?1 and gender like ?2", nativeQuery = true)
    List<Client> getAllClientsByStatus(boolean is_active, String gender);

    @Query(value = "SELECT * FROM Clients WHERE gender like ?", nativeQuery = true)
    List<Client> getAllClientsByGender(String gender);

    @Query(value = "SELECT * FROM Clients WHERE join_date = ?1", nativeQuery = true)
    List<Client> getAllClientsByDate(LocalDate join_date);

    @Query(value = "SELECT * FROM Clients WHERE birthday = ?1", nativeQuery = true)
    List<Client> getAllClientsBirthday(String birthday);

    @Query(value = "select * from clients where clients.is_active =true and clients.id not in (select c_id from part where s_id = ?1)", nativeQuery = true)
    List<Client> getAllAvailableClients(int s_id);
}
