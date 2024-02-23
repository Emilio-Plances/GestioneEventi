package com.example.GestioneEventi.requests.userRequests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message="The email field cannot be blank")
    private String name;
    @NotBlank(message="The email field cannot be blank")
    private String surname;
    @NotBlank(message="The email field cannot be blank")
    @Email(message= "Please enter a mail")
    private String email;
    @NotBlank(message="The password field cannot be blank")
    @Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$",
            message = "Password must contain:\n-1 letter uppercase\n-1 letter lowercase\n-1 number\n1 special character")
    private String password;
}
