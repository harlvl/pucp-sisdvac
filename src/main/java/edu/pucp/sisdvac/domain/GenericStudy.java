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

import java.util.Collection;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "generic_study")
public class GenericStudy {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer phase;

    // no need to have advance here since it's a one-to-many relationship

    @OneToOne(cascade = CascadeType.ALL)
    private GenericStudyEvaluation evaluation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<AdverseEvent> adverseEvents;

    private String objectives;

    @Enumerated(EnumType.STRING)
    private SubjectType subjectModel;

    // for clinical studies this is always in vivo
    @Enumerated(EnumType.STRING)
    private AnimalStudyTypeEnum type;

    private Integer sampleSize; // subjects Total
    private Integer subjectsTotal;
    private Integer subjectsCompleted;
    private Integer subjectsAbandoned;
    private Integer subjectsFailed;
    private Integer maleCount;
    private Integer femaleCount;
    private Integer minAge;
    private Integer maxAge;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<TestVolunteer> testVolunteers; // these are humans

    private String ethicalGuidelines;

    private String ethicalGuidelinesUri; // should be a URI

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date endDate;

}
