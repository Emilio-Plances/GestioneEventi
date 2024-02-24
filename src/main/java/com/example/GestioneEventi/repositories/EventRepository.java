package com.example.GestioneEventi.repositories;

import com.example.GestioneEventi.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, PagingAndSortingRepository<Event,Long> {
    @Query("SELECT e FROM Event e WHERE e.maxMembers>SIZE(e.usersList)")
    Page<Event> findNotFull(Pageable pageable);
}
