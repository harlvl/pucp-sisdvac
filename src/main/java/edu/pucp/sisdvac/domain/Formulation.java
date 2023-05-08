package edu.pucp.sisdvac.domain;


import edu.pucp.sisdvac.domain.enums.FormulationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vaccine_formulation")
public class Formulation {
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private FormulationStatus status;

    @Column(name = "_order") // make sure this column is not called just "order" or it will throw a huge fucking error
    private Integer order; // used to keep track of which formulation was first

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<FormulationItem> items;
}
