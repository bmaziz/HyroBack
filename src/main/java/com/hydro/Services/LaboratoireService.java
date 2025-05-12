package com.hydro.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hydro.Entities.Laboratoire;
import com.hydro.Repositories.LaboratoireRepository;
@Service
public class LaboratoireService {
	private final LaboratoireRepository laboratoireRepository;

    public LaboratoireService(LaboratoireRepository laboratoireRepository) {
        this.laboratoireRepository = laboratoireRepository;
    }

    public List<Laboratoire> getAllLaboratoires() {
        return laboratoireRepository.findAll();
    }
}
