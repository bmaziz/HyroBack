package com.hydro.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hydro.Entities.Profil;

public interface ProfilRepository  extends JpaRepository<Profil, String>, JpaSpecificationExecutor<Profil>  {

}
