package com.example.GestioneEventi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "events")
public class Event {
    private String location;
    @Column(name = "max_members")
    private int maxMembers;
    private LocalDate date;
    @ManyToMany
    @JoinTable(name="event_user",
    joinColumns = @JoinColumn(name="events_fk"),
    inverseJoinColumns = @JoinColumn(name="users_fk"))
    private List<User> userList=new ArrayList<>();
}
