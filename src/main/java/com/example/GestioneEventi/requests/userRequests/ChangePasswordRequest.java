package com.example.GestioneEventi.requests.userRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message="The old password field cannot be blank")
    private String oldPassword;
    @NotBlank(message="The new password field cannot be blank")
    @Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$",
            message = "Password must contain:\n-1 letter uppercase\n-1 letter lowercase\n-1 number\n1 special character")
    private String newPassword;
}
