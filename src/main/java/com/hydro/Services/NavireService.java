package com.hydro.Services;

import org.springframework.stereotype.Service;

import com.hydro.Entities.Navire;
import com.hydro.Repositories.NavireRepository;

import java.util.List;

@Service
public class NavireService {

    private final NavireRepository navireRepository;

    public NavireService(NavireRepository navireRepository) {
        this.navireRepository = navireRepository;
    }

    public List<Navire> getAllNavires() {
        return navireRepository.findAll();
    }
}

