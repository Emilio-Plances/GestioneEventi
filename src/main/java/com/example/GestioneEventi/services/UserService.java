package com.example.GestioneEventi.services;

import com.example.GestioneEventi.entities.User;
import com.example.GestioneEventi.exceptions.NotFoundException;
import com.example.GestioneEventi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) throws NotFoundException {
        Optional<User> optionalUser=userRepository.findByEmail(email);
        if(optionalUser.isEmpty())throw new NotFoundException("This user doesn't exist");
        return optionalUser.get();
    }
}
