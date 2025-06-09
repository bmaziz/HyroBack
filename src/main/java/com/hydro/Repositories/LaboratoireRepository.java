package com.hydro.Repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydro.Entities.Laboratoire;

public interface LaboratoireRepository extends JpaRepository<Laboratoire, String>{
	Optional<Laboratoire> findByLab(String lab);
	List<Laboratoire> findByInstitutionContainingIgnoreCase(String institution);
}
