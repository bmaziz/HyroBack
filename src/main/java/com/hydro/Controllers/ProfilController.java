package com.hydro.Controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hydro.Entities.Profil;
import com.hydro.Services.ProfilService;

@RestController
@RequestMapping("/api/profils")
public class ProfilController {
	 @Autowired
	    private ProfilService profilService;

	    @GetMapping("/filter")
	    public ResponseEntity<List<Profil>> getFilteredProfils(
	        @RequestParam(required = false) String region,
	        @RequestParam(required = false) Double latMin,
	        @RequestParam(required = false) Double latMax,
	        @RequestParam(required = false) Double lonMin,
	        @RequestParam(required = false) Double lonMax,
	        @RequestParam(required = false) Double profMin,
	        @RequestParam(required = false) Double profMax,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin
	    ) {
	        List<Profil> profils = profilService.getFilteredProfils(
	                region, latMin, latMax, lonMin, lonMax, profMin, profMax, dateDebut, dateFin
	        );
	        return ResponseEntity.ok(profils);
	    }
}
