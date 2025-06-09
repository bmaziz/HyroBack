package com.hydro.dto;

import com.hydro.Entities.Profil;
import lombok.*;

@Data
@AllArgsConstructor
public class ProfilAvecStatutDto {
    private Profil profil;
    private String statut;
}