package com.hydro.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hydro.Entities.Mesure;
import com.hydro.Entities.Parametres;

public interface MesureRepository extends JpaRepository<Mesure, String>{
	@Query("SELECT DISTINCT m.codeParam FROM Mesure m WHERE m.station.profil.idProfil = :profilId")
	List<Parametres> findDistinctParametresByProfilId(@Param("profilId") String profilId);
	@Query("SELECT m FROM Mesure m WHERE m.station.profil.idProfil IN :idProfils")
	List<Mesure> findMesuresByProfils(@Param("idProfils") List<String> idProfils);

	
}
