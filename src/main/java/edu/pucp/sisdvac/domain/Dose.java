package edu.pucp.sisdvac.domain;

import edu.pucp.sisdvac.domain.enums.DoseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dose")
public class Dose {
    @Id
    @GeneratedValue
    private Integer id;

    private String ageGroup; // ex: 6-11 years

    private Double size; // in mcg (micrograms)
    private Integer interval; // days between this and the next dose

    @Enumerated(EnumType.STRING)
    private DoseType type;
}
