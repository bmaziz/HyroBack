package com.hydro.dto;

import com.hydro.Entities.Role;

import lombok.Data;

@Data
public class RegisterRequest {
    private String login;
    private String adresse;
    private String email;
    private String institution;
    private String password;
    private String typeInst;
    private Role  role;
}
