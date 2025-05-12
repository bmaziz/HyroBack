package com.hydro.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DataCentre {
    @Id
    private String codeCentre;
    @ManyToOne
    @JoinColumn(name = "codePays")
    private Pays pays;
    
    private String nomCentre;
    private String prefixe;
    
}

