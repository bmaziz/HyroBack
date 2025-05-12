package com.hydro.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hydro.Entities.Mesure;
import com.hydro.Entities.Parametres;
import com.hydro.Entities.Profil;

public interface MesureRepository extends JpaRepository<Mesure, String>{
	@Query("SELECT DISTINCT m.codeParam FROM Mesure m WHERE m.profil.idProfil = :profil")
	List<Parametres> findDistinctParametresByProfilId(@Param("profil") String profil);

}
