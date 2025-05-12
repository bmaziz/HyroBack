package com.hydro.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hydro.Entities.Parametres;
import com.hydro.Repositories.ParametreRepository;

@Service
public class ParametresService {

    @Autowired
    private ParametreRepository parametresRepository;

    public List<Parametres> getAllParametres() {
        return parametresRepository.findAll();
    }
}
