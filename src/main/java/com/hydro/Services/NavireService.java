package com.hydro.Services;

import com.hydro.Entities.Navire;
import com.hydro.Repositories.NavireRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NavireService {

    private final NavireRepository navireRepository;

    public NavireService(NavireRepository navireRepository) {
        this.navireRepository = navireRepository;
    }

    public List<Navire> getAllNavires() {
        return navireRepository.findAll();
    }

    public Navire ajouter(Navire navire) {
        return navireRepository.save(navire);
    }

    public Navire modifier(String codeNavire, Navire navire) {
        Navire existant = navireRepository.findById(codeNavire)
                .orElseThrow(() -> new RuntimeException("Navire non trouvé : " + codeNavire));
        existant.setNomNavire(navire.getNomNavire());
        existant.setLongueur(navire.getLongueur());
        existant.setPuissance(navire.getPuissance());
        existant.setTonnage(navire.getTonnage());
        existant.setPay(navire.getPay());
        return navireRepository.save(existant);
    }
    
    public Optional<Navire> getById(String codeNavire) {
        return navireRepository.findById(codeNavire);
    }

    public void supprimer(String codeNavire) {
        navireRepository.deleteById(codeNavire);
    }

    public List<Navire> chercherParNom(String nomNavire) {
        return navireRepository.findByNomNavireContainingIgnoreCase(nomNavire);
    }
}
