package edu.pucp.sisdvac.domain;

import edu.pucp.sisdvac.domain.enums.EvaluationFormulaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "generic_evaluation_item")
public class GenericEvaluationItem {
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvaluationFormulaEnum type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal detail;
}
