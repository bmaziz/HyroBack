package com.hydro.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hydro.Entities.Parametres;
import com.hydro.Services.ParametresService;

@RestController
@RequestMapping("/api/parametres")

public class ParametresController {

    @Autowired
    private ParametresService parametresService;

    @GetMapping
    public ResponseEntity<List<Parametres>> getAllParametres() {
        return ResponseEntity.ok(parametresService.getAllParametres());
    }
}