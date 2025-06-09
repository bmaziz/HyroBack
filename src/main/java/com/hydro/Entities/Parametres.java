package com.hydro.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Parametres {
    @Id
    private String codeParam;
    @ManyToOne
    @JoinColumn(name="code_type")
    private DataType data_type;
    private String libelleParam;
    private String unite;
}

