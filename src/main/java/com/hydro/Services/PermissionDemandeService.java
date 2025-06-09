package com.hydro.Services;

import com.hydro.Entities.PermissionDemande;
import com.hydro.Entities.Profil;
import com.hydro.Entities.Utilisateur;
import com.hydro.Repositories.PermissionDemandeRepository;
import com.hydro.Repositories.ProfilRepository;
import com.hydro.Repositories.UtilisateurRepository;
import com.hydro.dto.ProfilAvecStatutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionDemandeService {

    @Autowired
    private PermissionDemandeRepository permissionDemandeRepository;

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public String ajouterDemande(String idProfil, String login) {
        Profil profil = profilRepository.findById(idProfil)
                .orElseThrow(() -> new RuntimeException("❌ Profil introuvable."));
        Utilisateur utilisateur = utilisateurRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("❌ Utilisateur introuvable."));

        if (permissionDemandeRepository.existsByUtilisateurAndProfil(utilisateur, profil)) {
            return "❌ Demande déjà soumise pour ce profil.";
        }

        PermissionDemande demande = new PermissionDemande();
        demande.setUtilisateur(utilisateur);
        demande.setProfil(profil);
        demande.setDateDemande(new Date());
        demande.setStatut("PENDING");

        permissionDemandeRepository.save(demande);
        return "✅ Demande ajoutée avec succès.";
    }

    public String traiterDemande(Long idDemande, String nouveauStatut) {
        PermissionDemande demande = permissionDemandeRepository.findById(idDemande)
                .orElseThrow(() -> new RuntimeException("❌ Demande introuvable."));

        if (!nouveauStatut.equals("ACCEPTED") && !nouveauStatut.equals("REJECTED")) {
            return "❌ Statut invalide. Utilisez 'ACCEPTED' ou 'REJECTED'.";
        }

        demande.setStatut(nouveauStatut);
        permissionDemandeRepository.save(demande);
        return "✅ Demande mise à jour.";
    }

    public List<Profil> getProfilsAccessiblesParUtilisateur(String login) {
        Utilisateur utilisateur = utilisateurRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("❌ Utilisateur introuvable."));

        List<Profil> publics = profilRepository.findAll().stream()
                .filter(p -> p.getCompagne().getDisponibilite().getDisp_code().equalsIgnoreCase("P"))
                .collect(Collectors.toList());

        List<Profil> autorises = permissionDemandeRepository.findByUtilisateur(utilisateur).stream()
                .filter(d -> d.getStatut().equals("ACCEPTED"))
                .map(PermissionDemande::getProfil)
                .collect(Collectors.toList());

        publics.addAll(autorises);
        return publics;
    }
    public List<PermissionDemande> getAllDemandes() {
        return permissionDemandeRepository.findAll();
    }
    public List<PermissionDemande> getDemandesEnAttente() {
        return permissionDemandeRepository.findAll()
                .stream()
                .filter(d -> "PENDING".equalsIgnoreCase(d.getStatut()))
                .collect(Collectors.toList());
    }

    public List<ProfilAvecStatutDto> getProfilsAvecStatut(String login) {
        Utilisateur utilisateur = utilisateurRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("❌ Utilisateur introuvable."));

        List<PermissionDemande> demandes = permissionDemandeRepository.findByUtilisateur(utilisateur);

        return profilRepository.findAll().stream().map(p -> {
            Optional<PermissionDemande> demande = demandes.stream()
                    .filter(d -> d.getProfil().getIdProfil().equals(p.getIdProfil()))
                    .findFirst();

            String statut = demande.map(PermissionDemande::getStatut).orElse("non_demandé");

            return new ProfilAvecStatutDto(p, statut);
        }).collect(Collectors.toList());
    }
}
