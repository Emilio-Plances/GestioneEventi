package com.example.GestioneEventi.requests.eventRequests;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
@Data
public class EventPatchRequest {
    @Pattern(regexp = ".*[^ ].*",message = "Enter a valid name!")
    private String name;
    @Pattern(regexp = ".*[^ ].*",message = "Enter a valid location!")
    private String location;
    private Integer maxMembers;
    @Pattern(regexp = "((?:19|20)\\\\d\\\\d)-(0?[1-9]|1[012])-([12][0-9]|3[01]|0?[1-9])", message = "Enter a valid date! (YYYY-MM-DD)")
    private LocalDate date;
    @Pattern(regexp = ".*[^ ].*",message = "Enter a valid description!")
    private String description;
}
