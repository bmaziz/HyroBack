package com.hydro.Entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MesureId implements Serializable {
    private String idMesure;
    @ManyToOne
    @JoinColumn(name = "idCampagne")
    private String idCampagne;
}
