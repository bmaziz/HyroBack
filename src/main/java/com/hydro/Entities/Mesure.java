package com.hydro.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
	    name = "mesure",
	    uniqueConstraints = @UniqueConstraint(columnNames = {"idProfil", "codeParam", "deph"})
	)
public class Mesure {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ou AUTO, SEQUENCE, selon ta config
	private Long idMesure;

    @ManyToOne
    @JoinColumn(name = "codeParam")
    private Parametres codeParam;  
    @ManyToOne
    @JoinColumn(name = "idProfil")
    private Profil profil;
    @ManyToOne
    @JoinColumn(name = "flag")
    private Flag flag;
    @ManyToOne
    @JoinColumn(name = "code_type")
    private DataType code_type;
    private Float deph;
    private Float valeur;
}