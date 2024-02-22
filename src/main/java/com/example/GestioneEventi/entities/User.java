package com.example.GestioneEventi.entities;

import com.example.GestioneEventi.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="users")
public class User {
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany(mappedBy = "userList")
    private List<Event> eventList;
}
