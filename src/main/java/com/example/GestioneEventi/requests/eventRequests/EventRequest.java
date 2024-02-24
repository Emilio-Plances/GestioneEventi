package com.example.GestioneEventi.requests.eventRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
@Data
public class EventRequest {
    @NotBlank(message = "Enter a name")
    private String name;
    @NotBlank(message = "Enter a location")
    private String location;
    @NotNull(message = "Enter a max number of members")
    private Integer maxMembers;
    @NotNull(message = "Enter a date")
    private LocalDate date;
    @NotNull(message="Enter a description")
    private String description;
}
