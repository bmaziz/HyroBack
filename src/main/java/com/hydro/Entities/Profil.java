package com.hydro.Entities;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    // ✅ expose uniquement l'ID de la campagne dans le JSON
    @JsonProperty("compagneId")
    public String getCompagneId() {
        return compagne != null ? compagne.getIdCampagne() : null;
    }
    @JsonProperty("dispCode")
    public String getdispcode() {
        return compagne != null ? compagne.getDisponibilite().getDisp_code() : null;
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


}
