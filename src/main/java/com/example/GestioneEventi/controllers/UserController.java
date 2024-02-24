package com.example.GestioneEventi.controllers;

import com.example.GestioneEventi.entities.User;
import com.example.GestioneEventi.exceptions.AlreadyAssignedException;
import com.example.GestioneEventi.exceptions.FullEventException;
import com.example.GestioneEventi.exceptions.NotFoundException;
import com.example.GestioneEventi.exceptions.BadRequestException;
import com.example.GestioneEventi.exceptions.UnauthorizedException;
import com.example.GestioneEventi.requests.userRequests.ChangePasswordRequest;
import com.example.GestioneEventi.requests.userRequests.UserPatchRequest;
import com.example.GestioneEventi.responses.DefaultResponse;
import com.example.GestioneEventi.security.JwtTools;
import com.example.GestioneEventi.services.EventService;
import com.example.GestioneEventi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTools jwtTools;

    @PatchMapping("/{id}/password")
    public ResponseEntity<DefaultResponse> changePassword(@PathVariable long id, @RequestBody @Validated ChangePasswordRequest passRequest, BindingResult bindingResult) throws NotFoundException, UnauthorizedException, BadRequestException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        User user=userService.findById(id);
        if(!encoder.matches(passRequest.getOldPassword(),user.getPassword())) throw new UnauthorizedException("Wrong password");
        userService.setPassword(id, passRequest.getNewPassword());
        return DefaultResponse.noObject("Password changed",HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable long id, @RequestBody @Validated UserPatchRequest patchRequest, BindingResult bindingResult) throws BadRequestException, NotFoundException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.full("Success!",userService.update(id,patchRequest),HttpStatus.OK);
    }
    @PatchMapping("/{id}/upgrade")
    public ResponseEntity<DefaultResponse> upgrade(@PathVariable long id) throws NotFoundException {
        return DefaultResponse.full("User upgraded",userService.upgrade(id),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<DefaultResponse> getAll(Pageable pageable){
        return DefaultResponse.noMessage(userService.findAll(pageable),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> getUserById(@PathVariable long id) throws NotFoundException {
        return DefaultResponse.noMessage(userService.findById(id),HttpStatus.OK);
    }
    @GetMapping("/params")
    public ResponseEntity<DefaultResponse> getUserByEmail(@RequestParam String email) throws NotFoundException {
        return DefaultResponse.noMessage(userService.findByEmail(email),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable long id) throws NotFoundException {
        userService.delete(id);
        return DefaultResponse.noObject("User deleted!",HttpStatus.OK);
    }
    @PatchMapping("/{id}/{eventId}")
    public ResponseEntity<DefaultResponse> partecipation(@PathVariable long id, @PathVariable long eventId) throws com.example.GestioneEventi.exceptions.BadRequestException, FullEventException, NotFoundException, AlreadyAssignedException {
        eventService.addPartecipation(id,eventId);
        return DefaultResponse.noObject("Success",HttpStatus.OK);
    }
}
