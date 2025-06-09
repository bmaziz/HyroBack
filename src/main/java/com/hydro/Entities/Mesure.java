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
	    uniqueConstraints = @UniqueConstraint(columnNames = {"idStation", "codeParam",})
	)
public class Mesure {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ou AUTO, SEQUENCE, selon ta config
	private Long idMesure;

    @ManyToOne
    @JoinColumn(name = "codeParam")
    private Parametres codeParam;  
    @ManyToOne
    @JoinColumn(name = "idStation")
    
    private Station station;
    @ManyToOne
    @JoinColumn(name = "flag")
    private Flag flag;
    @ManyToOne
    @JoinColumn(name = "code_type")
    private DataType code_type;
    private Float valeur;
}