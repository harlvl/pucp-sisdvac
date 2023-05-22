package edu.pucp.sisdvac.domain;

import edu.pucp.sisdvac.domain.enums.EvaluationFormulaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "evaluation_item")
public class EvaluationItem {
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvaluationFormulaEnum type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal detail;
}
