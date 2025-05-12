package com.hydro.Entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
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

    private String refCompagne;
    private String data_link;
    private int code_ref;
    private Date dateDebut;
    private Date dateFin;

    // ✅ Ajout de la relation avec les profils
    @OneToMany(mappedBy = "compagne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Profil> profils;
}
