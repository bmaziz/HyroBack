package com.hydro.Controllers;

import com.hydro.Entities.Projet;
import com.hydro.Services.ProjetService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
public class ProjetController {

    private final ProjetService projetService;

    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @PostMapping
    public Projet ajouter(@RequestBody Projet projet) {
        return projetService.ajouter(projet);
    }

    @PutMapping("/{nomProjet}")
    public Projet modifier(@PathVariable String nomProjet, @RequestBody Projet projet) {
        return projetService.modifier(nomProjet, projet);
    }

    @DeleteMapping("/{nomProjet}")
    public void supprimer(@PathVariable String nomProjet) {
        projetService.supprimer(nomProjet);
    }

    @GetMapping("/{nomProjet}")
    public ResponseEntity<Projet> getById(@PathVariable String nomProjet) {
        return projetService.getById(nomProjet)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Projet> getTous() {
        return projetService.getTous();
    }

    @GetMapping("/search")
    public List<Projet> chercherParNom(@RequestParam String nomProjet) {
        return projetService.chercherParNom(nomProjet);
    }
}
