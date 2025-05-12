package com.hydro.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Station {
    @Id
    private Integer idStation;
    
    @ManyToOne
    @JoinColumn(name = "idCampagne")
    private Compagne compagne;
    
    @ManyToOne
    @JoinColumn(name = "idProfil")
    private Profil profil;
    
    private Integer flagStation;
}