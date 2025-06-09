package com.hydro.Services;

import com.hydro.Entities.Scientifique;
import com.hydro.Repositories.ScientifiqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScientifiqueService {

    private final ScientifiqueRepository scientifiqueRepository;

    public ScientifiqueService(ScientifiqueRepository scientifiqueRepository) {
        this.scientifiqueRepository = scientifiqueRepository;
    }

    public Scientifique ajouter(Scientifique scientifique) {
        return scientifiqueRepository.save(scientifique);
    }

    public Scientifique modifier(String nom_s, Scientifique scientifique) {
        Scientifique existant = scientifiqueRepository.findById(nom_s)
                .orElseThrow(() -> new RuntimeException("Scientifique non trouvé : " + nom_s));
        existant.setNom(scientifique.getNom());
        existant.setPrenom(scientifique.getPrenom());
        existant.setSigleInst(scientifique.getSigleInst());
        return scientifiqueRepository.save(existant);
    }

    public void supprimer(String nom_s) {
        scientifiqueRepository.deleteById(nom_s);
    }
    public Optional<Scientifique> getById(String nom_s) {
        return scientifiqueRepository.findById(nom_s);
    }
    public List<Scientifique> getTous() {
        return scientifiqueRepository.findAll();
    }

    public List<Scientifique> chercherParNom(String nom) {
        return scientifiqueRepository.findByNomContainingIgnoreCase(nom);
    }
}
