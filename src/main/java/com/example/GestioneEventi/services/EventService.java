package com.example.GestioneEventi.services;

import com.example.GestioneEventi.entities.Event;
import com.example.GestioneEventi.exceptions.NotFoundException;
import com.example.GestioneEventi.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    public Event findById(long id) throws NotFoundException {
        Optional<Event> optionalEvent=eventRepository.findById(id);
        if(optionalEvent.isEmpty())throw new NotFoundException("This user doesn't exist");
        return optionalEvent.get();
    }
}
