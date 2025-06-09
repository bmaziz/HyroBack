package com.hydro.Services;

import com.hydro.Entities.Laboratoire;
import com.hydro.Repositories.LaboratoireRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratoireService {

    private final LaboratoireRepository laboratoireRepository;

    public LaboratoireService(LaboratoireRepository laboratoireRepository) {
        this.laboratoireRepository = laboratoireRepository;
    }

    public List<Laboratoire> getAllLaboratoires() {
        return laboratoireRepository.findAll();
    }

    public Laboratoire ajouter(Laboratoire labo) {
        return laboratoireRepository.save(labo);
    }

    public Laboratoire modifier(String lab, Laboratoire labo) {
        Laboratoire existant = laboratoireRepository.findById(lab)
                .orElseThrow(() -> new RuntimeException("Laboratoire non trouvé : " + lab));
        existant.setInstitution(labo.getInstitution());
        existant.setVille(labo.getVille());
        return laboratoireRepository.save(existant);
    }
    public Optional<Laboratoire> getById(String lab) {
        return laboratoireRepository.findById(lab);
    }
    public void supprimer(String lab) {
        laboratoireRepository.deleteById(lab);
    }

    public List<Laboratoire> chercherParInstitution(String institution) {
        return laboratoireRepository.findByInstitutionContainingIgnoreCase(institution);
    }
}
