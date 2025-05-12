package com.hydro.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydro.Entities.Projet;

public interface ProjetRepository extends JpaRepository<Projet, String>{
Optional<Projet> findByNomProjet(String nomProjet);
}
