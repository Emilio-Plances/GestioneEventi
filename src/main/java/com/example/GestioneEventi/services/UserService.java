package com.example.GestioneEventi.services;

import com.example.GestioneEventi.entities.Event;
import com.example.GestioneEventi.entities.User;
import com.example.GestioneEventi.enums.Role;
import com.example.GestioneEventi.exceptions.AlreadyAssignedException;
import com.example.GestioneEventi.exceptions.FullEventException;
import com.example.GestioneEventi.exceptions.NotFoundException;
import com.example.GestioneEventi.repositories.UserRepository;
import com.example.GestioneEventi.requests.userRequests.RegisterRequest;
import com.example.GestioneEventi.requests.userRequests.UserPatchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }
    public User findById(long id) throws NotFoundException {
        Optional<User> optionalUser=userRepository.findById(id);
        if(optionalUser.isEmpty())throw new NotFoundException("This user doesn't exist");
        return optionalUser.get();
    }
    public User findByEmail(String email) throws NotFoundException {
        Optional<User> optionalUser=userRepository.findByEmail(email);
        if(optionalUser.isEmpty())throw new NotFoundException("This user doesn't exist");
        return optionalUser.get();
    }
    public User register(RegisterRequest registerRequest){
        User user=new User();
        user.setEmail(registerRequest.getEmail());
        user.setRole(Role.USER);
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        return userRepository.save(user);
    }
    public User update(long id, UserPatchRequest patchRequest) throws NotFoundException {
        User user=findById(id);
        if(patchRequest.getName()!=null) user.setName(patchRequest.getName());
        if(patchRequest.getSurname()!=null) user.setSurname(patchRequest.getSurname());
        if(patchRequest.getEmail()!=null) user.setEmail(patchRequest.getEmail());
        return userRepository.save(user);
    }
    public void setPassword(long id,String password) throws NotFoundException {
        User user=findById(id);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }
    public User upgrade(long id) throws NotFoundException {
        User user=findById(id);
        user.setRole(Role.ORGANIZER);
        return userRepository.save(user);
    }
    public void delete(long id) throws NotFoundException {
        User user=findById(id);
        userRepository.delete(user);
    }
}
