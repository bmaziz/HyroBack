package com.hydro.Services;

import com.hydro.Entities.Region;
import com.hydro.Repositories.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public Region ajouter(Region region) {
        return regionRepository.save(region);
    }

    public Region modifier(String codeReg, Region region) {
        Region existante = regionRepository.findById(codeReg)
                .orElseThrow(() -> new RuntimeException("Région non trouvée : " + codeReg));
        existante.setReg(region.getReg());
        return regionRepository.save(existante);
    }

    public void supprimer(String codeReg) {
        regionRepository.deleteById(codeReg);
    }
    public Optional<Region> getById(String codeReg) {
        return regionRepository.findById(codeReg);
    }
    public List<Region> getToutes() {
        return regionRepository.findAll();
    }

    public List<Region> chercherParNom(String reg) {
        return regionRepository.findByRegContainingIgnoreCase(reg);
    }
}
