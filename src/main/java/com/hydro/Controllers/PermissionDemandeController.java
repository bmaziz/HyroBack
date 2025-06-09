package com.hydro.Controllers;

import com.hydro.Entities.Profil;
import com.hydro.Services.PermissionDemandeService;
import com.hydro.dto.ProfilAvecStatutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionDemandeController {

    @Autowired
    private PermissionDemandeService permissionDemandeService;

    @PostMapping("/demander")
    public ResponseEntity<String> demanderAcces(@RequestParam String idProfil, @RequestParam String login) {
        try {
            return ResponseEntity.ok(permissionDemandeService.ajouterDemande(idProfil, login));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/traiter")
    public ResponseEntity<String> traiterDemande(@RequestParam Long idDemande, @RequestParam String statut) {
        try {
            return ResponseEntity.ok(permissionDemandeService.traiterDemande(idDemande, statut));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profils/{login}")
    public ResponseEntity<?> getProfils(@PathVariable String login) {
        try {
            return ResponseEntity.ok(permissionDemandeService.getProfilsAccessiblesParUtilisateur(login));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profils-etat/{login}")
    public ResponseEntity<?> getProfilsAvecStatut(@PathVariable String login) {
        try {
            return ResponseEntity.ok(permissionDemandeService.getProfilsAvecStatut(login));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllDemandes() {
        try {
            return ResponseEntity.ok(permissionDemandeService.getAllDemandes());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/en-attente")
    public ResponseEntity<?> getDemandesEnAttente() {
        try {
            return ResponseEntity.ok(permissionDemandeService.getDemandesEnAttente());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
