package com.example.GestioneEventi.controllers;

import com.example.GestioneEventi.entities.User;
import com.example.GestioneEventi.exceptions.BadRequestException;
import com.example.GestioneEventi.exceptions.NotFoundException;
import com.example.GestioneEventi.exceptions.UnauthorizedException;
import com.example.GestioneEventi.requests.userRequests.LoginRequest;
import com.example.GestioneEventi.requests.userRequests.RegisterRequest;
import com.example.GestioneEventi.responses.DefaultResponse;
import com.example.GestioneEventi.responses.LoginResponse;
import com.example.GestioneEventi.security.JwtTools;
import com.example.GestioneEventi.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTools jwtTools;
    @PostMapping("/register")
    public ResponseEntity<DefaultResponse> register(@RequestBody @Validated RegisterRequest registerRequest, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.full("Success!",userService.register(registerRequest), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest, BindingResult bindingResult) throws BadRequestException, NotFoundException, UnauthorizedException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        User user=userService.findByEmail(loginRequest.getEmail());
        if (!encoder.matches(loginRequest.getPassword(),user.getPassword())) throw new UnauthorizedException("Wrong username/password");
        return LoginResponse.full(jwtTools.createToken(user),user,HttpStatus.OK);
    }
}
