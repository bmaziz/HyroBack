package com.hydro.Entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Compagne {

    @Id
    private String idCampagne;

    @ManyToOne
    @JoinColumn(name = "codeCentre")
    private DataCentre dataCentre;

    @ManyToOne
    @JoinColumn(name = "codeNavire")
    private Navire navire;

    @ManyToOne
    @JoinColumn(name = "codePays")
    private Pays pays;

    @ManyToOne
    @JoinColumn(name = "codeReg")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "codeProjet")
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "labo")
    private Laboratoire laboratoire;

    @ManyToOne
    @JoinColumn(name = "nom_s")
    private Scientifique scientifique;

    @ManyToOne
    @JoinColumn(name = "dispCode")
    private Disponibilite disponibilite;

    @ManyToOne
    @JoinColumn(name = "code_type")
    private DataType data_type;

    private String nomCampagne;
    private String refCompagne;
    private String data_link;
    private int code_ref;
    private Date dateDebut;
    private Date dateFin;
    private int nb_profil;
    private String qc;

    @OneToMany(mappedBy = "compagne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Profil> profils;

    @Override
    public String toString() {
        return "Compagne{" +
                "idCampagne='" + idCampagne + '\'' +
                ", nomCampagne='" + nomCampagne + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nb_profil=" + nb_profil +
                '}';
    }
}
