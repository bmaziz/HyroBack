package com.hydro.Controllers;

import com.hydro.Entities.Parametres;
import com.hydro.Services.MesureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesures")
public class MesureController {

    @Autowired
    private MesureService mesureService;

    @GetMapping("/parametres/{idProfil}")
    public List<Parametres> getParametresByProfil(@PathVariable String idProfil) {
        return mesureService.getDistinctParametresByProfilId(idProfil);
    }
}
