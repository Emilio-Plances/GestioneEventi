package com.example.GestioneEventi.controllers;

import com.example.GestioneEventi.entities.User;
import com.example.GestioneEventi.exceptions.AlreadyAssignedException;
import com.example.GestioneEventi.exceptions.FullEventException;
import com.example.GestioneEventi.exceptions.NotFoundException;
import com.example.GestioneEventi.exceptions.UnauthorizedException;
import com.example.GestioneEventi.requests.userRequests.PartecipationRequest;
import com.example.GestioneEventi.requests.userRequests.ChangePasswordRequest;
import com.example.GestioneEventi.requests.userRequests.LoginRequest;
import com.example.GestioneEventi.requests.userRequests.RegisterRequest;
import com.example.GestioneEventi.requests.userRequests.UserPatchRequest;
import com.example.GestioneEventi.responses.DefaultResponse;
import com.example.GestioneEventi.responses.LoginResponse;
import com.example.GestioneEventi.security.JwtTools;
import com.example.GestioneEventi.services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTools jwtTools;
    @PostMapping("/auth/register")
    public ResponseEntity<DefaultResponse> register(@RequestBody @Validated RegisterRequest registerRequest, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.full("Success!",userService.register(registerRequest), HttpStatus.CREATED);
    }
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest, BindingResult bindingResult) throws BadRequestException, NotFoundException, UnauthorizedException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        User user=userService.findByEmail(loginRequest.getEmail());
        if (!encoder.matches(loginRequest.getPassword(),user.getPassword())) throw new UnauthorizedException("Wrong username/password");
        return LoginResponse.full(jwtTools.createToken(user),user,HttpStatus.OK);
    }
    @PatchMapping("/users/{id}/password")
    public ResponseEntity<DefaultResponse> changePassword(@PathVariable long id, @RequestBody @Validated ChangePasswordRequest passRequest, BindingResult bindingResult) throws NotFoundException, UnauthorizedException, BadRequestException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        User user=userService.findById(id);
        if(!encoder.matches(passRequest.getOldPassword(),user.getPassword())) throw new UnauthorizedException("Passwords must match");
        userService.setPassword(id, passRequest.getNewPassword());
        return DefaultResponse.noObject("Password changed",HttpStatus.OK);
    }
    @PatchMapping("/users/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable long id, @RequestBody @Validated UserPatchRequest patchRequest, BindingResult bindingResult) throws BadRequestException, NotFoundException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.full("Success!",userService.update(id,patchRequest),HttpStatus.OK);
    }
    @PatchMapping("/users/{id}/upgrade")
    public ResponseEntity<DefaultResponse> upgrade(@PathVariable long id) throws NotFoundException {
        userService.upgrade(id);
        return DefaultResponse.noObject("User upgraded",HttpStatus.OK);
    }
    @PatchMapping("/users/{id}/partecipation")
    public ResponseEntity<DefaultResponse> partecipation(@PathVariable long id, @RequestBody PartecipationRequest partecipationRequest, BindingResult bindingResult) throws BadRequestException, FullEventException, NotFoundException, AlreadyAssignedException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        userService.addPartecipation(id,partecipationRequest.getEventId());
        return DefaultResponse.noObject("Success",HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<DefaultResponse> getAll(Pageable pageable){
        return DefaultResponse.noMessage(userService.findAll(pageable),HttpStatus.OK);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<DefaultResponse> getUserById(@PathVariable long id) throws NotFoundException {
        return DefaultResponse.noMessage(userService.findById(id),HttpStatus.OK);
    }
    @GetMapping("/users/{email}")
    public ResponseEntity<DefaultResponse> getUserByEmail(@PathVariable String email) throws NotFoundException {
        return DefaultResponse.noMessage(userService.findByEmail(email),HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable long id) throws NotFoundException {
        userService.delete(id);
        return DefaultResponse.noObject("User deleted!",HttpStatus.OK);
    }
}
