package com.hydro.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompagneDTO {
    private String idCompagne;
    private Date dateDebut;
    private Date dateFin;
}