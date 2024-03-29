package com.example.GestioneEventi.entities;

import com.example.GestioneEventi.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "users_sequence")
    @SequenceGenerator(name = "users_sequence",allocationSize = 1)
    private long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonIgnore
    @ManyToMany(mappedBy = "usersList",cascade = CascadeType.REMOVE)
    private List<Event> eventList;
    @JsonIgnore
    @OneToMany(mappedBy = "creator",cascade = CascadeType.REMOVE)
    private List<Event> createdEvents;
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return null;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
