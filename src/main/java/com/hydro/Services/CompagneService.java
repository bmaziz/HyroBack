package com.hydro.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hydro.Entities.Compagne;
import com.hydro.Entities.Parametres;
import com.hydro.Entities.Profil;
import com.hydro.Repositories.CompagneRepository;
import com.hydro.Repositories.MesureRepository;
import com.hydro.dto.CompagneDTO;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompagneService {

    @Autowired
    private CompagneRepository compagneRepository;
    @Autowired
    private MesureRepository mesureRepository;
    public List<CompagneDTO> rechercherCampagnes(String navire, String labo, LocalDate startDate, LocalDate endDate) { 
    	System.out.println(startDate);
    	List<Object[]> rawResults = compagneRepository.searchCampagnes(navire, labo, startDate, endDate);
    	System.out.println(labo);
        List<CompagneDTO> dtos = new ArrayList<>();

        for (Object[] row : rawResults) {
            String id = (String) row[0];
            Date dateDebut = (Date) row[1];
            Date dateFin = (Date) row[2];

            dtos.add(new CompagneDTO(id, dateDebut, dateFin));
        }

        return dtos;
    }
    public List<Compagne> getAllCompagnes() {
        return compagneRepository.findAll();
    }

    public Optional<Compagne> getCompagneById(String id) {
        return compagneRepository.findById(id);
    }

    public Compagne saveCompagne(Compagne campagne) {
        return compagneRepository.save(campagne);
    }

    public void deleteCompagne(String id) {
        compagneRepository.deleteById(id);
    }

    public Compagne updateCompagne(String id, Compagne updatedCampagne) {
        return compagneRepository.findById(id)
            .map(compagne -> {
                compagne.setRefCompagne(updatedCampagne.getIdCampagne());
                compagne.setDateDebut(updatedCampagne.getDateDebut());
                compagne.setDateFin(updatedCampagne.getDateFin());
                compagne.setDataCentre(updatedCampagne.getDataCentre());
                compagne.setNavire(updatedCampagne.getNavire());
                compagne.setPays(updatedCampagne.getPays());
                compagne.setRegion(updatedCampagne.getRegion());
                compagne.setProjet(updatedCampagne.getProjet());
                compagne.setLaboratoire(updatedCampagne.getLaboratoire());
                compagne.setScientifique(updatedCampagne.getScientifique());
                compagne.setDisponibilite(updatedCampagne.getDisponibilite());
                return compagneRepository.save(compagne);
            })
            .orElse(null);
    }


    public List<String> findCompagneIdsBetweenDates(LocalDate dateDebut, LocalDate dateFin) {
        return compagneRepository.findAll().stream()
            .filter(c -> {
                LocalDate debut = c.getDateDebut().toLocalDate(); // Conversion
                LocalDate fin = c.getDateFin().toLocalDate();     // Conversion
                return !debut.isBefore(dateDebut) && !fin.isAfter(dateFin);
            })
            .map(Compagne::getIdCampagne)
            .collect(Collectors.toList());
    }


    public Compagne findCompagneFullById(String id) {
        System.out.println("Recherche campagne avec ID : " + id);

        Compagne campagne = compagneRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Campagne introuvable avec l'ID : " + id));

        List<Profil> profils = campagne.getProfils();

        if (profils != null && !profils.isEmpty()) {
            for (Profil profil : profils) {
                System.out.println("Profil ID : " + profil.getIdProfil());

                List<Parametres> parametres = mesureRepository.findDistinctParametresByProfilId(profil.getIdProfil());

                if (parametres != null && !parametres.isEmpty()) {
                    System.out.println("Paramètres pour le profil " + profil.getIdProfil() + " :");
                    for (Parametres param : parametres) {
                        System.out.println(" - " + param.getCodeParam());
                    }
                } else {
                    System.out.println("Aucun paramètre trouvé pour le profil " + profil.getIdProfil());
                }

                // Optionnel : tu peux les affecter dans le profil si tu veux les retourner dans le JSON
                profil.setParametres(parametres);;
                System.out.println(profil.getParametres());
            }
            campagne.setProfils(profils);

        } else {
            System.out.println("Aucun profil associé à cette campagne.");
        }

        return campagne;
    }


    public byte[] generateCampagneFile(List<String> campagneIds) {
        StringBuilder content = new StringBuilder();

        for (String id : campagneIds) {
            Compagne campagne = findCompagneFullById(id);
            if (campagne != null) {
                content.append(formatCampagne(campagne)).append("\n\n");
            }
        }

        return content.toString().getBytes(StandardCharsets.UTF_8);
    }

    private String formatCampagne(Compagne c) {
        StringBuilder sb = new StringBuilder();

        sb.append("**************************************************\n");
        sb.append(String.format("           Campagne : %s\n", c.getIdCampagne()));
        sb.append("**************************************************\n\n");

        sb.append(String.format("Ref Campagne : %s\n", c.getIdCampagne()));
        sb.append(String.format("Nom Campagne : %s\n", c.getNomCampagne()));
        sb.append(String.format("Ref Bateau : %s\n", c.getNavire() != null ? c.getNavire().getCodeNavire() + " " + c.getNavire().getNomNavire() : "N/A"));
        sb.append(String.format("Ref Pays : %s %s\n", c.getPays() != null ? c.getPays().getCodePays() : "N/A", c.getPays() != null ? c.getPays().getNomPays() : "N/A"));
        sb.append(String.format("Ref Laboratoire : %s\n", c.getLaboratoire() != null ? c.getLaboratoire().getLab() : "N/A"));
        sb.append(String.format("Responsable : %s\n", c.getScientifique() != null ? c.getScientifique().getNom() + " " + c.getScientifique().getPrenom() : "N/A"));
        sb.append(String.format("Ref Projet : %s\n", c.getProjet() != null ? c.getProjet().getNomProjet() : "N/A"));
        sb.append(String.format("Nb. Profile : %d\n", c.getNb_profil()));

        sb.append("\n--------------------------------------------------\n");
        sb.append("                Liste Des Profils                \n");
        sb.append("--------------------------------------------------\n");

        int index = 1;
        for (Profil p : c.getProfils()) {
            sb.append(String.format("Profile N° %d :\n", index++));
            sb.append(String.format("Ref. du profile: %s\n", p.getIdProfil()));
            sb.append(String.format("Station : Lat = %s° %s   Lon = %s° %s\n",
                    p.getDeg_lat(), p.getDirect_lat(), p.getDeg_long(), p.getDirect_long()));
            sb.append(String.format("Date et Temps : %s à %s\n", p.getDateProfil(),p.getTempsProfil()));
            sb.append(String.format("Profondeur : %s\n", p.getDepth()));

            sb.append("Paramètres :");
            for(Parametres param:p.getParametres()) {
            	sb.append(param.getLibelleParam()+"|");
            }
            sb.append(String.format("\nData Type : %s(%s)\n", c.getData_type().getCodeType(),c.getData_type().getLibelleType()));
            sb.append("--------------------------------------------------\n");
        }

        return sb.toString();
    }


}
