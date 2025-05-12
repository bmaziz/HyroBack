package com.hydro.Services;

import com.hydro.Entities.*;
import com.hydro.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CliParserService {

    @Autowired private CompagneRepository compagneRepository;
    @Autowired private MesureRepository mesureRepository;
    @Autowired private ParametreRepository parametreRepository;

    @Autowired private NavireRepository navireRepository;
    @Autowired private RegionRepository regionRepository;
    @Autowired private LaboratoireRepository laboratoireRepository;
    @Autowired private PaysRepository paysRepository;
    @Autowired private ScientifiqueRepository scientifiqueRepository;
    @Autowired private ProjetRepository projetRepository;
    @Autowired private DataCentreRepository dataCentreRepository;
    @Autowired private DataTypeRepository dataTypeRepository;
    @Autowired private DisponibiliteRepository disponibiliteRepository;
    @Autowired private ProfilRepository profilRepository;

    public void parseAndSaveCliFile(MultipartFile file) {
        try (
            InputStream inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        ) {
            // Initialisation des variables de campagne
            String idCompagne = "", codeSecondaire = "", navire = "", codePays = "";
            String dateDebut = "", dateFin = "", zone = "";
            String labo = "", responsable = "", projet = "", archiving = "", availability = "";
            String typeDonnee = "", nbProfils = "", qcGlobal = "";
            String dateStr = "";
            String timeStr = "";
            String directLat = "";
            String directLon = "";
            String depthStr="";
            String tempsProfil="";
            String idProfil="";
            int degLat = 0;
            float minLat = 0;
            int degLon = 0;
            float minLon = 0;
            float lat = 0;
            float lon = 0;
            float depth = 0;
            int nbParam = 0;
            int nbParamCourant=0;
            int lDeph = 0;

            Date date=null;
            List<String> paramCodes = new ArrayList<>();
            List<String> valuesParam = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("*TU") && !line.contains("Data Type=")) {
                    String[] parts = line.trim().split("\\s+");
                    idCompagne = parts[0].replace("*", "");
                    codeSecondaire = parts.length > 1 ? parts[1] : "";
                    if (line.contains("N/O")) {
                        navire = line.substring(line.indexOf("N/O")).trim();
                    }

                } else if (line.matches("\\d{2}/\\d{2}/\\d{4}.*")) {
                    String[] parts = line.trim().split("\\s+");
                    dateDebut = parts[0];
                    dateFin = parts[1];
                    zone = line.substring(line.indexOf(parts[1]) + parts[1].length()).trim();

                } else if (line.matches("^\\d+\\s+.*")) {
                    String[] parts = line.trim().split("\\s+", 2);
                    if (parts.length == 2) {
                        codePays = parts[0];
                        labo = parts[1];
                    }

                } else if (line.contains("Project=")) {
                    responsable = line.substring(0, line.indexOf("Project=")).trim();
                    projet = line.substring(line.indexOf("Project=") + 8).trim();

                } else if (line.contains("Regional Archiving=") && line.contains("Availability=")) {
                    archiving = line.split("Regional Archiving=")[1].split("Availability=")[0].trim();
                    availability = line.split("Availability=")[1].trim();

                } else if (line.contains("Data Type=") && line.contains("QC=")) {
                    typeDonnee = line.split("Data Type=")[1].split("n=")[0].trim();
                    nbProfils = line.split("n=")[1].split("QC=")[0].trim();
                    qcGlobal = line.split("QC=")[1].trim();
                    saveCompagneFromCliData(idCompagne, navire, zone, labo, codePays, responsable, projet, archiving, availability, dateDebut, dateFin);
               
                }
                else if (line.startsWith("*TU") && line.contains("Data Type=")) {
                     idProfil = line.substring(1, line.indexOf("Data Type=")).trim();

                    String dataType = line.substring(line.indexOf("Data Type=") + "Data Type=".length()).trim();

                    // Affichage ou traitement
                    System.out.println("Code TU : " + idProfil);
                    System.out.println("Type de données : " + dataType);
                
            

                }
                else if (line.startsWith("*DATE=")) {
                     dateStr = line.substring(line.indexOf("DATE=") + 5, line.indexOf("TIME=")).trim();
                    

                     timeStr = line.substring(line.indexOf("TIME=") + 5, line.indexOf("LAT=")).trim();
                    String latPart = line.substring(line.indexOf("LAT=") + 4, line.indexOf("LON=")).trim();
                    String lonPart = line.substring(line.indexOf("LON=") + 4, line.indexOf("DEPTH=")).trim();
                     depthStr = line.substring(line.indexOf("DEPTH=") + 6, line.indexOf("QC=")).trim();
                     depth= Float.parseFloat(depthStr);
                    // Traitement Latitude
                    String[] latTokens = latPart.split(" ");
                     directLat = latTokens[0].substring(0, 1); // N
                     degLat = Integer.parseInt(latTokens[0].substring(1)); // 37
                     minLat = Float.parseFloat(latTokens[1]); // 27.96

                    // Traitement Longitude
                    String[] lonTokens = lonPart.split(" ");
                     directLon = lonTokens[0].substring(0, 1); // E
                     degLon = Integer.parseInt(lonTokens[0].substring(1)); // 10
                     minLon = Float.parseFloat(lonTokens[1]); // 04.02

                    // Conversion en décimal
                     lat = degLat + (minLat / 60f);
                     lon = degLon + (minLon / 60f);

                    // Format date + temps
                   // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                     //date = LocalDate.parse(dateStr, formatter);
                     tempsProfil = timeStr.substring(0, 2) + ":" + timeStr.substring(2);

                    // Affichage complet
                    System.out.println("----- Extraction complète -----");
                    System.out.println("Date Profil     : " + dateStr);
                    System.out.println("Temps Profil    : " + tempsProfil);
                    System.out.println("Lat (° décimal) : " + lat);
                    System.out.println("Longitude (°)   : " + lon);
                    System.out.println("Profondeur (m)  : " + depthStr);
                    System.out.println("Détails Latitude:");
                    System.out.println("  Direction     : " + directLat);
                    System.out.println("  Degrés        : " + degLat);
                    System.out.println("  Minutes       : " + minLat);
                    System.out.println("Détails Longitude:");
                    System.out.println("  Direction     : " + directLon);
                    System.out.println("  Degrés        : " + degLon);
                    System.out.println("  Minutes       : " + minLon);
                }
                else if (line.startsWith("*NB PARAMETERS=")) {
                
                    String nbParamStr = line.substring(line.indexOf("NB PARAMETERS=") + 14, line.indexOf("RECORD LINES=")).trim();
                    nbParamCourant = Integer.parseInt(nbParamStr);
                    System.out.println("NB_PARAM mis à jour : " + nbParamCourant);
                    Compagne c=compagneRepository.findById(idCompagne) .orElseThrow(() -> new RuntimeException("compagne introuvable: "));
                    Profil profil = new Profil();
                    profil.setIdProfil(idProfil);
                    profil.setCompagne(c);
             
                    profil.setDateProfil(Date.valueOf(convertDate2(dateStr)));
                    profil.setTempsProfil(tempsProfil);
                    profil.setLat(lat);
                    profil.setLongitude(lon);
                    profil.setDepth(depth);
                    profil.setDeg_lat(degLat);
                    profil.setMin_lat(minLat);
                    profil.setDirect_lat(directLat);
                    profil.setDeg_long(degLon);
                    profil.setMin_long(minLon);
                    profil.setDirect_long(directLon);
                    profil.setNb_param(nbParam);
                    profilRepository.save(profil);
                   
                    System.out.println("✔ Profil ajouté : " + profil);
                    paramCodes.clear();
                    for (int i = 0; i < nbParamCourant; i++) {
                        line = reader.readLine();
                        String paramCode = line.substring(1, line.indexOf(" ")).trim();
                        paramCodes.add(paramCode);
          

                        if (paramCode.equals("DEPH")) {
                            lDeph = i;
                        }
                    }



                }else if (line.startsWith(" ")) {
                    String l = line.trim();

                    String[] tokens = l.split("\\s+"); // découpe tous les champs numériques
                    for (int i = 0; i < Math.min(tokens.length, nbParamCourant); i++) {
                    	
                    	if(i!=lDeph) {
                    		Mesure mesure=new Mesure();
                    		Profil profil = profilRepository.findById(idProfil)
                                    .orElseThrow(() -> new RuntimeException("profil introuvable: "));
                    		Parametres parametre=parametreRepository.findById(paramCodes.get(i)) 
                    				.orElseThrow(() -> new RuntimeException("parametre introuvable: "));
                    		DataType dataType=dataTypeRepository.findById(typeDonnee)
                    				.orElseThrow(() -> new RuntimeException("dataType introuvable: "));
                    		



                            mesure.setProfil(profil);
                            mesure.setCode_type(dataType);
                            mesure.setCodeParam(parametre);
                            mesure.setDeph(Float.parseFloat(tokens[lDeph]));
                            mesure.setValeur(Float.parseFloat(tokens[i]));
                            mesureRepository.save(mesure);
                    	}
                    }

                    System.out.println(valuesParam);
                }

            
               
            }
                

            // Affichage de la campagne
            System.out.println("========= DONNÉES DE LA CAMPAGNE =========");
            System.out.println("Code Profil       : " + idCompagne);
            System.out.println("Code Secondaire   : " + codeSecondaire);
            System.out.println("Navire            : " + navire);
            System.out.println("Date Début   for (int i = 0; i < nbParamCourant; i++) {\r\n"
            		+ "    // traitement d'une ligne de mesure\r\n"
            		+ "}\r\n"
            		+ "     : " + dateDebut);
            System.out.println("Date Fin          : " + dateFin);
            System.out.println("Zone              : " + zone);
            System.out.println("Code Pays         : " + codePays);
            System.out.println("Laboratoire       : " + labo);
            System.out.println("Responsable       : " + responsable);
            System.out.println("Projet            : " + projet);
            System.out.println("Centre Données    : " + archiving);
            System.out.println("Disponibilité     : " + availability);
            System.out.println("Type Donnée       : " + typeDonnee);
            System.out.println("Nb Profils        : " + nbProfils);
            System.out.println("QC Global         : " + qcGlobal);
            System.out.println("==========================================");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier CLI", e);
        }
    }

    private void saveCompagneFromCliData(
            String idCompagne, String nomNavire, String nomRegion, String nomLabo, String codePays,
            String nomScientifique, String nomProjet, String codeCentre, String codeDisponibilite,
            String debut, String fin) {

        Compagne compagne = new Compagne();
        compagne.setIdCampagne(idCompagne);

        Navire navire = navireRepository.findByNomNavire(nomNavire)
                .orElseThrow(() -> new RuntimeException("Navire introuvable: " + nomNavire));
        Region region = regionRepository.findByReg(nomRegion)
                .orElseThrow(() -> new RuntimeException("Région introuvable: " + nomRegion));
        Laboratoire laboratoire = laboratoireRepository.findByLab(nomLabo)
                .orElseThrow(() -> new RuntimeException("Laboratoire introuvable: " + nomLabo));
        Scientifique scientifique = scientifiqueRepository.findById(nomScientifique)
                .orElseThrow(() -> new RuntimeException("Scientifique introuvable: " + nomScientifique));
        Projet projet = projetRepository.findByNomProjet(nomProjet)
                .orElseThrow(() -> new RuntimeException("Projet introuvable: " + nomProjet));
        DataCentre dataCentre = dataCentreRepository.findByCodeCentre(codeCentre)
                .orElseThrow(() -> new RuntimeException("Centre introuvable: " + codeCentre));
        Disponibilite disponibilite = disponibiliteRepository.findById(codeDisponibilite)
                .orElseThrow(() -> new RuntimeException("Disponibilité introuvable: " + codeDisponibilite));
        Pays pays = paysRepository.findById(codePays)
                .orElseThrow(() -> new RuntimeException("Pays introuvable: " + codePays));

        compagne.setNavire(navire);
        compagne.setRegion(region);
        compagne.setLaboratoire(laboratoire);
        compagne.setScientifique(scientifique);
        compagne.setProjet(projet);
        compagne.setDataCentre(dataCentre);
        compagne.setDisponibilite(disponibilite);
        compagne.setPays(pays);
        compagne.setDateDebut(Date.valueOf(convertDate(debut)));
        compagne.setDateFin(Date.valueOf(convertDate(fin)));
        compagne.setRefCompagne("CLI-" + idCompagne);
        compagne.setCode_ref(0);
        compagne.setData_link(null);

        compagneRepository.save(compagne);
    }

    private LocalDate convertDate(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    private LocalDate convertDate2(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("ddMMyyyy"));
    }
}
