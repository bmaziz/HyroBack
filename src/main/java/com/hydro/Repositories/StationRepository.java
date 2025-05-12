package com.hydro.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hydro.Entities.Station;

public interface StationRepository extends JpaRepository<Station, String>{

}
