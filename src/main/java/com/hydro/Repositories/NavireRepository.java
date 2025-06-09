package com.hydro.Repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydro.Entities.Navire;

public interface NavireRepository extends JpaRepository<Navire, String>{
	Optional<Navire> findByNomNavire(String nomNavire);
	List<Navire> findByNomNavireContainingIgnoreCase(String nomNavire);

}
