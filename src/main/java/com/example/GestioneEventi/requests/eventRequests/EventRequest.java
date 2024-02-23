package com.example.GestioneEventi.requests.eventRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
@Data
public class EventRequest {
    @NotBlank(message = "Enter a name")
    private String name;
    @NotBlank(message = "Enter a location")
    private String location;
    @NotBlank(message = "Enter a max number of members")
    private Integer maxMembers;
    @NotBlank(message = "Enter a date")
    @Pattern(regexp = "((?:19|20)\\d\\d)-(0?[1-9]|1[012])-([12][0-9]|3[01]|0?[1-9])",message = "Enter a valid date! (YYYY-MM-DD)")
    private LocalDate date;
}
