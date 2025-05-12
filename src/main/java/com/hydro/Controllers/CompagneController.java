package com.hydro.Controllers;

import com.hydro.Entities.Compagne;
import com.hydro.Services.CompagneService;
import com.hydro.dto.CompagneDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compagnes")
public class CompagneController {

    @Autowired
    private CompagneService compagneService;

    // ✅ GET : toutes les campagnes
    @GetMapping
    public List<Compagne> getAllCompagnes() {
        return compagneService.getAllCompagnes();
    }
    @GetMapping(value = "/search", produces = "application/json")
    public List<CompagneDTO> searchCampagnes(
            @RequestParam(required = false) String navire,
            @RequestParam(required = false) String laboratoire,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

     System.out.println(dateDebut);

        return compagneService.rechercherCampagnes(navire, laboratoire, dateDebut, dateFin);
    }

    // ✅ GET : une campagne par ID (tous les détails)
    @GetMapping("/{id}")
    public Compagne getCompagneById(@PathVariable String id) {
        return compagneService.findCompagneFullById(id);
    }
    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadCampagnes(@RequestBody List<String> campagneIds) {
        byte[] fileContent = compagneService.generateCampagneFile(campagneIds);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.attachment()
            .filename("campagnes_detail.txt").build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileContent);
    }
    // ✅ POST : ajouter une compagne
    @PostMapping
    public Compagne createCompagne(@RequestBody Compagne campagne) {
        return compagneService.saveCompagne(campagne);
    }

    // ✅ PUT : mettre à jour une campagne
    @PutMapping("/{id}")
    public Compagne updateCompagne(@PathVariable String id, @RequestBody Compagne updatedCampagne) {
        return compagneService.updateCompagne(id, updatedCampagne);
    }

    // ✅ DELETE : supprimer une campagne
    @DeleteMapping("/{id}")
    public void deleteCompagne(@PathVariable String id) {
        compagneService.deleteCompagne(id);
    }

    // ✅ GET : rechercher entre deux dates et retourner seulement les ID
    @GetMapping("/searchByDate")
    public List<String> searchCompagnesBetweenDates(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return compagneService.findCompagneIdsBetweenDates(start, end);
    }
}
