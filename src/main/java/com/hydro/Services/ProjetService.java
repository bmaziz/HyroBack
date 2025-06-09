package com.hydro.Services;

import com.hydro.Entities.Projet;
import com.hydro.Repositories.ProjetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetService {

    private final ProjetRepository projetRepository;

    public ProjetService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    public Projet ajouter(Projet projet) {
        return projetRepository.save(projet);
    }

    public Projet modifier(String nomProjet, Projet projet) {
        Projet existant = projetRepository.findById(nomProjet)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé : " + nomProjet));
        existant.setNomProjet(projet.getNomProjet());
        return projetRepository.save(existant);
    }

    public void supprimer(String nomProjet) {
        projetRepository.deleteById(nomProjet);
    }
    public Optional<Projet> getById(String nomProjet) {
        return projetRepository.findById(nomProjet);
    }
    public List<Projet> getTous() {
        return projetRepository.findAll();
    }

    public List<Projet> chercherParNom(String nomProjet) {
        return projetRepository.findByNomProjetContainingIgnoreCase(nomProjet);
    }
}
