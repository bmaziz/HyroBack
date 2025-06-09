package com.hydro.Services;

import com.hydro.Entities.DataCentre;
import com.hydro.Repositories.DataCentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataCentreService {

    @Autowired
    private DataCentreRepository repository;

    public DataCentre ajouter(DataCentre centre) {
        return repository.save(centre);
    }

    public DataCentre modifier(String codeCentre, DataCentre centre) {
        DataCentre existant = repository.findById(codeCentre)
                .orElseThrow(() -> new RuntimeException("Centre non trouvé : " + codeCentre));
        existant.setNomCentre(centre.getNomCentre());
        existant.setPrefixe(centre.getPrefixe());
        existant.setPays(centre.getPays());
        return repository.save(existant);
    }

    public void supprimer(String codeCentre) {
        repository.deleteById(codeCentre);
    }
    public Optional<DataCentre> getById(String codeCentre) {
        return repository.findById(codeCentre);
    }
    public List<DataCentre> getTous() {
        return repository.findAll();
    }

    public List<DataCentre> chercherParNom(String nomCentre) {
        return repository.findByNomCentreContainingIgnoreCase(nomCentre);
    }
}
