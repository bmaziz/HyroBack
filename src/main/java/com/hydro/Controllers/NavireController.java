package com.hydro.Controllers;

import com.hydro.Entities.Navire;
import com.hydro.Services.NavireService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/navires")
public class NavireController {

    private final NavireService navireService;

    public NavireController(NavireService navireService) {
        this.navireService = navireService;
    }

    @GetMapping
    public List<Navire> getAllNavires() {
        return navireService.getAllNavires();
    }

    @PostMapping
    public Navire ajouter(@RequestBody Navire navire) {
        return navireService.ajouter(navire);
    }

    @PutMapping("/{codeNavire}")
    public Navire modifier(@PathVariable String codeNavire, @RequestBody Navire navire) {
        return navireService.modifier(codeNavire, navire);
    }

    @DeleteMapping("/{codeNavire}")
    public void supprimer(@PathVariable String codeNavire) {
        navireService.supprimer(codeNavire);
    }
    @GetMapping("/{codeNavire}")
    public ResponseEntity<Navire> getById(@PathVariable String codeNavire) {
        return navireService.getById(codeNavire)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Navire> chercherParNom(@RequestParam String nom) {
        return navireService.chercherParNom(nom);
    }
}
