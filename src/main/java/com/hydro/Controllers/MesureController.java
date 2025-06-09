package com.hydro.Controllers;

import com.hydro.Entities.Mesure;
import com.hydro.Entities.Parametres;
import com.hydro.Entities.Profil;
import com.hydro.Entities.Station;
import com.hydro.Services.MesureService;
import com.hydro.dto.MesureFilterRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mesures")
public class MesureController {

    @Autowired
    private MesureService mesureService;

    @GetMapping("/parametres/{idProfil}")
    public List<Parametres> getParametresByProfil(@PathVariable String idProfil) {
        return mesureService.getDistinctParametresByProfilId(idProfil);
    }
    @PostMapping("/grouped-by-profil-station")
    public ResponseEntity<Map<Profil, Map<Station, List<Mesure>>>> getMesuresGroupedByProfilThenStation(
            @RequestBody MesureFilterRequest request) {

        Map<Profil, Map<Station, List<Mesure>>> result =
                mesureService.getMesuresGroupedByProfilThenStation(
                        request.getIdProfils(),
                        request.getCodeParams());

        return ResponseEntity.ok(result);
    }
    @PostMapping("/export-txt")
    public ResponseEntity<Resource> exportMesuresTxt(@RequestBody MesureFilterRequest request) throws IOException {
        File file = mesureService.exportMesuresToTxt(request.getIdProfils(), request.getCodeParams());

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mesures.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }


}
