package com.hydro.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hydro.Services.CliParserService;

@RestController
@RequestMapping("/api/cli")
public class CliFileController {

    private final CliParserService cliParserService;

    public CliFileController(CliParserService cliParserService) {
        this.cliParserService = cliParserService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadCliFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            cliParserService.parseAndSaveCliFile(file);
            response.put("message", "✅ Fichier lu avec succès !");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "❌ Erreur lors de la lecture du fichier : " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
