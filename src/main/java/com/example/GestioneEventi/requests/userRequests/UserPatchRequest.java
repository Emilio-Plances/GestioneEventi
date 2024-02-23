package com.example.GestioneEventi.requests.userRequests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserPatchRequest {
    @Pattern(regexp = ".*[^ ].*",message = "Enter a valid name!")
    private String name;
    @Pattern(regexp = ".*[^ ].*",message = "Enter a valid name!")
    private String surname;
    @Email(message = "Enter a valid mail!")
    private String email;
}
