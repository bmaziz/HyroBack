package com.hydro.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MesureArchive {
    @Id
    private String codeParam;
    
    @ManyToOne
    @JoinColumn(name = "idCampagne")
    private Compagne compagne;
    
    @ManyToOne
    @JoinColumn(name = "idProfil")
    private Profil profil;
    
    @ManyToOne
    @JoinColumn(name = "idStation")
    private Station station;
    
    private Float valeur;
}
