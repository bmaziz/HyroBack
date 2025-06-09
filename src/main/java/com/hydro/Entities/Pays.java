package com.hydro.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pays {
	@Id
	@Column(name = "code_pays")
	private String codePays;
	@Column(name = "nom_pays")
    private String nomPays; 
	
}
