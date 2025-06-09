package com.hydro.Controllers;

import com.hydro.Entities.DataCentre;
import com.hydro.Services.DataCentreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datacentres")
public class DataCentreController {

    @Autowired
    private DataCentreService service;

    @PostMapping
    public DataCentre ajouter(@RequestBody DataCentre centre) {
        return service.ajouter(centre);
    }

    @PutMapping("/{codeCentre}")
    public DataCentre modifier(@PathVariable String codeCentre, @RequestBody DataCentre centre) {
        return service.modifier(codeCentre, centre);
    }

    @DeleteMapping("/{codeCentre}")
    public void supprimer(@PathVariable String codeCentre) {
        service.supprimer(codeCentre);
    }
    @GetMapping("/{codeCentre}")
    public ResponseEntity<DataCentre> getById(@PathVariable String codeCentre) {
        return service.getById(codeCentre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<DataCentre> getTous() {
        return service.getTous();
    }

    @GetMapping("/search")
    public List<DataCentre> chercherParNom(@RequestParam String nomCentre) {
        return service.chercherParNom(nomCentre);
    }
}
