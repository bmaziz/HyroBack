package com.hydro.Services;

import com.hydro.Entities.Mesure;
import com.hydro.Entities.Parametres;
import com.hydro.Entities.Profil;
import com.hydro.Entities.Station;
import com.hydro.Repositories.MesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MesureService {

    @Autowired
    private MesureRepository mesureRepository;

    public List<Parametres> getDistinctParametresByProfilId(String idProfil) {
        return mesureRepository.findDistinctParametresByProfilId(idProfil);
    }

    public Map<Profil, Map<Station, List<Mesure>>> getMesuresGroupedByProfilThenStation(
            List<String> idProfils,
            List<String> codeParams) {

        List<Mesure> mesures = mesureRepository.findMesuresByProfils(idProfils);

        return mesures.stream()
                .filter(m -> codeParams.contains(m.getCodeParam().getCodeParam()))
                .collect(Collectors.groupingBy(
                        m -> m.getStation().getProfil(), // Group by Profil object
                        Collectors.groupingBy(Mesure::getStation)
                ));
    }

    public File exportMesuresToTxt(List<String> idProfils, List<String> codeParams) throws IOException {
        Map<Profil, Map<Station, List<Mesure>>> grouped =
                getMesuresGroupedByProfilThenStation(idProfils, codeParams);

        File file = File.createTempFile("mesures_export_", ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for (Map.Entry<Profil, Map<Station, List<Mesure>>> profilEntry : grouped.entrySet()) {
                Profil profil = profilEntry.getKey();

                // ➤ Écriture des détails du profil
                writer.write("PROFIL ID     : " + profil.getIdProfil()); writer.newLine();
                writer.write("DATE          : " + profil.getDateProfil()); writer.newLine();
                writer.write("LATITUDE      : " + profil.getLat()); writer.newLine();
                writer.write("LONGITUDE     : " + profil.getLongitude()); writer.newLine();
                writer.write("----------------------------------------------"); writer.newLine();

                // ➤ Récupérer les paramètres uniques
                Set<String> codeParamsSet = new LinkedHashSet<>();
                for (List<Mesure> mesures : profilEntry.getValue().values()) {
                    for (Mesure mesure : mesures) {
                        codeParamsSet.add(mesure.getCodeParam().getCodeParam());
                    }
                }

                // ➤ Écrire l'en-tête des paramètres
                for (String code : codeParamsSet) {
                    writer.write(String.format(Locale.US, "%-10s", code));
                }
                writer.newLine();

                // ➤ Écrire les valeurs station par station
                for (Map.Entry<Station, List<Mesure>> stationEntry : profilEntry.getValue().entrySet()) {
                    Map<String, Float> mesuresMap = new HashMap<>();
                    for (Mesure mesure : stationEntry.getValue()) {
                        mesuresMap.put(mesure.getCodeParam().getCodeParam(), mesure.getValeur());
                    }

                    for (String code : codeParamsSet) {
                        Float valeur = mesuresMap.getOrDefault(code, null);
                        if (valeur != null) {
                            writer.write(String.format(Locale.US, "%-10.4f", valeur));
                        } else {
                            writer.write(String.format(Locale.US, "%-10s", "N/A"));
                        }
                    }
                    writer.newLine();
                }

                writer.newLine(); // Séparer chaque profil par une ligne vide
            }

            return file;
        }
    }
}
