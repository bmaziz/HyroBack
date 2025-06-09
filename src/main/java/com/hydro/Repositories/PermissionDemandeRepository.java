package com.hydro.Repositories;

import com.hydro.Entities.PermissionDemande;
import com.hydro.Entities.Profil;
import com.hydro.Entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionDemandeRepository extends JpaRepository<PermissionDemande, Long> {
    boolean existsByUtilisateurAndProfil(Utilisateur utilisateur, Profil profil);
    List<PermissionDemande> findByUtilisateur(Utilisateur utilisateur);
}