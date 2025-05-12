package com.hydro.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Navire {
    @Id
    private String codeNavire;
    
    @ManyToOne
    @JoinColumn(name = "codePays")
    private Pays pay;
    
    private String nomNavire;
    private String longueur;
    private String puissance;
    private String tonnage;
    
  
}
