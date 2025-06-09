package com.hydro.Repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydro.Entities.Scientifique;

public interface ScientifiqueRepository extends JpaRepository<Scientifique, String>{
	List<Scientifique> findByNomContainingIgnoreCase(String nom);
}
