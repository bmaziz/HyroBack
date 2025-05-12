package com.hydro.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hydro.Entities.Compagne;

public interface CompagneRepository extends JpaRepository<Compagne, String> {
	@Query("SELECT idCampagne,dateDebut,dateFin FROM Compagne c " +
	           "WHERE (:navire IS NULL OR c.navire.codeNavire = :navire) " +
	           "AND (:labo IS NULL OR c.laboratoire.lab = :labo) " +
	           "AND c.dateDebut >= COALESCE(:dateDebut, c.dateDebut) " +
	           "AND c.dateFin <= COALESCE(:endDate, c.dateFin)")

	    List<Object[]> searchCampagnes(
	        @Param("navire") String navire,
	        @Param("labo") String labo,
	        @Param("dateDebut") LocalDate dateDebut,
	        @Param("endDate") LocalDate endDate
	    );
}
