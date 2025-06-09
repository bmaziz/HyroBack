package com.hydro.Controllers;

import com.hydro.Entities.Pays;
import com.hydro.Services.PaysService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pays")
public class PaysController {

    private final PaysService paysService;

    public PaysController(PaysService paysService) {
        this.paysService = paysService;
    }

    @PostMapping
    public Pays ajouter(@RequestBody Pays pays) {
        return paysService.ajouter(pays);
    }

    @PutMapping("/{codePays}")
    public Pays modifier(@PathVariable String codePays, @RequestBody Pays pays) {
        return paysService.modifier(codePays, pays);
    }

    @DeleteMapping("/{codePays}")
    public void supprimer(@PathVariable String codePays) {
        paysService.supprimer(codePays);
    }

    @GetMapping
    public List<Pays> getTous() {
        return paysService.getTous();
    }

    @GetMapping("/{codePays}")
    public ResponseEntity<Pays> getById(@PathVariable String codePays) {
        return paysService.getById(codePays)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/search")
    public List<Pays> chercher(@RequestParam String nom) {
        return paysService.chercherParNom(nom);
    }
}
