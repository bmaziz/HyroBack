package com.hydro.Repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydro.Entities.Region;

public interface RegionRepository extends JpaRepository<Region, String>{
	Optional<Region> findByReg(String reg);

}
