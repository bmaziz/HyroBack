package com.hydro.Controllers;

import com.hydro.Entities.Region;
import com.hydro.Services.RegionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @PostMapping
    public Region ajouter(@RequestBody Region region) {
        return regionService.ajouter(region);
    }

    @PutMapping("/{codeReg}")
    public Region modifier(@PathVariable String codeReg, @RequestBody Region region) {
        return regionService.modifier(codeReg, region);
    }

    @DeleteMapping("/{codeReg}")
    public void supprimer(@PathVariable String codeReg) {
        regionService.supprimer(codeReg);
    }

    @GetMapping("/{codeReg}")
    public ResponseEntity<Region> getById(@PathVariable String codeReg) {
        return regionService.getById(codeReg)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Region> getToutes() {
        return regionService.getToutes();
    }

    @GetMapping("/search")
    public List<Region> chercherParNom(@RequestParam String reg) {
        return regionService.chercherParNom(reg);
    }
}
