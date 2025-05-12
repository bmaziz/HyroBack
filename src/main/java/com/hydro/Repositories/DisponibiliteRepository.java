package com.hydro.Repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydro.Entities.Disponibilite;

public interface DisponibiliteRepository extends JpaRepository<Disponibilite, String>{
}
