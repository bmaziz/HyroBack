package com.hydro.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Scientifique {
    @Id
    private String nom_s;
    private String nom;
    private String prenom;
    private String sigleInst;
}

