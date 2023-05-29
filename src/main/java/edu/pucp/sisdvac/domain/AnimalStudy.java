package edu.pucp.sisdvac.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.pucp.sisdvac.domain.enums.AnimalStudyTypeEnum;
import edu.pucp.sisdvac.domain.enums.SubjectType;
import edu.pucp.sisdvac.utils.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "animal_study")
public class AnimalStudy {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(mappedBy = "animalStudy")
    private Advance advance;

    @OneToOne(cascade = CascadeType.ALL)
    private AnimalStudyEvaluation evaluation;

    private String objectives;

    @Enumerated(EnumType.STRING)
    private SubjectType animalModel;

    @Enumerated(EnumType.STRING)
    private AnimalStudyTypeEnum type;

    private Integer sampleSize;

    private String ethicalGuidelines;

    private String ethicalGuidelinesUri; // should be a URI

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date endDate;

    // study endpoints fields
}
