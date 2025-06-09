package com.hydro.Controllers;

import com.hydro.Entities.Laboratoire;
import com.hydro.Services.LaboratoireService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laboratoires")
public class LaboratoireController {

    private final LaboratoireService laboratoireService;

    public LaboratoireController(LaboratoireService laboratoireService) {
        this.laboratoireService = laboratoireService;
    }

    @GetMapping
    public List<Laboratoire> getAllLaboratoires() {
        return laboratoireService.getAllLaboratoires();
    }
    @GetMapping("/{lab}")
    public ResponseEntity<Laboratoire> getById(@PathVariable String lab) {
        return laboratoireService.getById(lab)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Laboratoire ajouter(@RequestBody Laboratoire labo) {
        return laboratoireService.ajouter(labo);
    }

    @PutMapping("/{lab}")
    public Laboratoire modifier(@PathVariable String lab, @RequestBody Laboratoire labo) {
        return laboratoireService.modifier(lab, labo);
    }

    @DeleteMapping("/{lab}")
    public void supprimer(@PathVariable String lab) {
        laboratoireService.supprimer(lab);
    }

    @GetMapping("/search")
    public List<Laboratoire> chercher(@RequestParam String institution) {
        return laboratoireService.chercherParInstitution(institution);
    }
}
