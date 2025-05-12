package com.hydro.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Utilisateur {
    @Id
    private String login;
    private String adresse;
    @Column(unique = true)
    private String email;
    private String institution;
    private String password;
    private String typeInst;

    @Enumerated(EnumType.STRING)
    private Role role;
}