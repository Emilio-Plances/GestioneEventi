package com.example.GestioneEventi.controllers;

import com.example.GestioneEventi.exceptions.AlreadyAssignedException;
import com.example.GestioneEventi.exceptions.FullEventException;
import com.example.GestioneEventi.exceptions.NotFoundException;
import com.example.GestioneEventi.exceptions.UnauthorizedException;
import com.example.GestioneEventi.requests.eventRequests.EventPatchRequest;
import com.example.GestioneEventi.requests.eventRequests.EventRequest;
import com.example.GestioneEventi.requests.userRequests.PartecipationRequest;
import com.example.GestioneEventi.responses.DefaultResponse;
import com.example.GestioneEventi.security.JwtTools;
import com.example.GestioneEventi.services.EventService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<DefaultResponse> save(@RequestBody @Validated EventRequest eventRequest, BindingResult bindingResult,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION)String auth) throws BadRequestException, NotFoundException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        String token=auth.substring(7);
        String email=jwtTools.extractEmailFromToken(token);
        return DefaultResponse.noMessage(eventService.save(email,eventRequest),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<DefaultResponse> getAll(Pageable pageable){
        return DefaultResponse.noMessage(eventService.findAll(pageable), HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable long id,@RequestBody @Validated EventPatchRequest eventPatchRequest,BindingResult bindingResult) throws BadRequestException, NotFoundException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.noMessage(eventService.update(id,eventPatchRequest),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> getById(@PathVariable long id) throws NotFoundException {
        return DefaultResponse.noMessage(eventService.findById(id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable long id){
        return DefaultResponse.noObject("Deleted",HttpStatus.OK);
    }
}
