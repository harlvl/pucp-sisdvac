package edu.pucp.sisdvac.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "formulation_evaluation")
public class FormulationEvaluation {
    @Id
    @GeneratedValue
    @Column(name = "evaluation_id")
    private Integer id;

    @OneToOne(mappedBy = "evaluation")
    private Formulation formulation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<EvaluationItem> items;
}
