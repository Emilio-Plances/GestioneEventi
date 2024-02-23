package com.example.GestioneEventi.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginRequest {
    @NotBlank(message="The email field cannot be blank")
    @Email(message= "Please enter a mail")
    private String email;
    @NotBlank(message="The password field cannot be blank")
    private String password;
}
