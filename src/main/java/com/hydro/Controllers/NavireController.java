package com.hydro.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hydro.Entities.Navire;
import com.hydro.Services.NavireService;

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
}