package com.hydro.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydro.Entities.Pays;

public interface PaysRepository extends JpaRepository<Pays, String>{
	List<Pays> findByNomPaysContainingIgnoreCase(String nom_pays);
}
