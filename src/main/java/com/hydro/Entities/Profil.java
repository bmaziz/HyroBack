package com.hydro.Entities;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profil {

    @Id
    private String idProfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCompagne")
    @JsonBackReference
    private Compagne compagne;

    @JsonProperty("compagneId")
    public String getCompagneId() {
        return compagne != null ? compagne.getIdCampagne() : null;
    }

    @JsonProperty("dispCode")
    public String getdispcode() {
        return compagne != null && compagne.getDisponibilite() != null
                ? compagne.getDisponibilite().getDisp_code()
                : null;
    }

    private Date dateProfil;
    private Float depth;
    private Float lat;
    private Float longitude;
    private String tempsProfil;
    private int deg_lat;
    private int deg_long;
    private int fdepth;
    private int fdt;
    private int flat;
    private int flong;
    private int global_profil_flag;
    private int nb_param;
    private Float min_lat;
    private Float min_long;
    private String direct_lat;
    private String direct_long;
    private String global_param_flag;

    @Transient
    private List<Parametres> parametres;

    @Override
    public String toString() {
        return "Profil{" +
                "idProfil='" + idProfil + '\'' +
                ", lat=" + lat +
                ", long=" + longitude +
                ", depth=" + depth +
                '}';
    }
}
