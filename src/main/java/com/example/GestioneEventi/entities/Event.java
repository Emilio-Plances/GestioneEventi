package com.example.GestioneEventi.entities;

import com.example.GestioneEventi.exceptions.FullEventException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "events_sequence")
    @SequenceGenerator(name = "events_sequence",allocationSize = 1)
    private long id;
    private String name;
    private String location;
    @Column(name = "max_members")
    private Integer maxMembers;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name="creator_fk")
    private User creator;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name="event_user",
    joinColumns = @JoinColumn(name="events_fk"),
    inverseJoinColumns = @JoinColumn(name="users_fk"))
    private List<User> usersList=new ArrayList<>();

    public void addUsersList(User user){
        usersList.add(user);
    }
    @Override
    public String toString() {
        return  "id=" + id +
                ", location='" + location + '\'' +
                ", maxMembers=" + maxMembers +
                ", date=" + date +
                ", usersList=" + usersList;
    }
}
