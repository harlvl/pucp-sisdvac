package edu.pucp.sisdvac.domain;


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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<FormulationItem> items;

    @OneToOne(mappedBy = "formulation")
    private Trial trial;
}
