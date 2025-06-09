package com.hydro.Repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydro.Entities.DataCentre;

public interface DataCentreRepository extends JpaRepository<DataCentre, String>{
	Optional<DataCentre> findByCodeCentre(String codeCentre);
	  List<DataCentre> findByNomCentreContainingIgnoreCase(String nomCentre);
	}

