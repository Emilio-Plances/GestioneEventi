package com.example.GestioneEventi.services;

import com.example.GestioneEventi.entities.Event;
import com.example.GestioneEventi.entities.User;
import com.example.GestioneEventi.exceptions.NotFoundException;
import com.example.GestioneEventi.repositories.EventRepository;
import com.example.GestioneEventi.requests.eventRequests.EventPatchRequest;
import com.example.GestioneEventi.requests.eventRequests.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserService userService;
    public Event findById(long id) throws NotFoundException {
        Optional<Event> optionalEvent=eventRepository.findById(id);
        if(optionalEvent.isEmpty())throw new NotFoundException("This user doesn't exist");
        return optionalEvent.get();
    }
    public Page<Event> findAll(Pageable pageable){
        return eventRepository.findAll(pageable);
    }
    public Event save(String email,EventRequest eventRequest) throws NotFoundException {
        Event event=new Event();
        User user=userService.findByEmail(email);
        event.setCreator(user);
        event.setName(eventRequest.getName());
        event.setLocation(event.getLocation());
        event.setDate(eventRequest.getDate());
        event.setMaxMembers(eventRequest.getMaxMembers());
        return eventRepository.save(event);
    }
    public Event update(long id, EventPatchRequest patchRequest) throws NotFoundException {
        Event event=findById(id);
        if(patchRequest.getName()!=null) event.setName(patchRequest.getName());
        if(patchRequest.getLocation()!=null) event.setLocation(patchRequest.getLocation());
        if(patchRequest.getDate()!=null) event.setDate(patchRequest.getDate());
        if(patchRequest.getMaxMembers()!=null) event.setMaxMembers(patchRequest.getMaxMembers());
        return eventRepository.save(event);
    }
    public void delete(long id) throws NotFoundException {
        Event event=findById(id);
        eventRepository.delete(event);
    }
}
