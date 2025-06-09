package com.hydro.Controllers;

import com.hydro.Entities.Scientifique;
import com.hydro.Services.ScientifiqueService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scientifiques")
public class ScientifiqueController {

    private final ScientifiqueService scientifiqueService;

    public ScientifiqueController(ScientifiqueService scientifiqueService) {
        this.scientifiqueService = scientifiqueService;
    }

    @PostMapping
    public Scientifique ajouter(@RequestBody Scientifique scientifique) {
        return scientifiqueService.ajouter(scientifique);
    }

    @PutMapping("/{nom_s}")
    public Scientifique modifier(@PathVariable String nom_s, @RequestBody Scientifique scientifique) {
        return scientifiqueService.modifier(nom_s, scientifique);
    }

    @DeleteMapping("/{nom_s}")
    public void supprimer(@PathVariable String nom_s) {
        scientifiqueService.supprimer(nom_s);
    }

    @GetMapping("/{nom_s}")
    public ResponseEntity<Scientifique> getById(@PathVariable String nom_s) {
        return scientifiqueService.getById(nom_s)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Scientifique> getTous() {
        return scientifiqueService.getTous();
    }

    @GetMapping("/search")
    public List<Scientifique> chercherParNom(@RequestParam String nom) {
        return scientifiqueService.chercherParNom(nom);
    }
}
