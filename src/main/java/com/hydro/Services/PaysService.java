package com.hydro.Services;

import com.hydro.Entities.Pays;
import com.hydro.Repositories.PaysRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaysService {

    private final PaysRepository paysRepository;

    public PaysService(PaysRepository paysRepository) {
        this.paysRepository = paysRepository;
    }

    public Pays ajouter(Pays pays) {
        return paysRepository.save(pays);
    }

    public Pays modifier(String codePays, Pays pays) {
        Pays existant = paysRepository.findById(codePays)
                .orElseThrow(() -> new RuntimeException("Pays non trouvé : " + codePays));
        existant.setNomPays(pays.getNomPays());
        return paysRepository.save(existant);
    }

    public void supprimer(String codePays) {
        paysRepository.deleteById(codePays);
    }
    public Optional<Pays> getById(String codePays) {
        return paysRepository.findById(codePays);
    }
    public List<Pays> getTous() {
        return paysRepository.findAll();
    }

    public List<Pays> chercherParNom(String nom) {
        return paysRepository.findByNomPaysContainingIgnoreCase(nom);
    }
}
