package edu.pucp.sisdvac.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.pucp.sisdvac.domain.enums.AnimalStudyTypeEnum;
import edu.pucp.sisdvac.domain.enums.SubjectType;
import edu.pucp.sisdvac.utils.Constants;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericStudyDto {
    private Integer id;

    private Integer researchId;

    private Integer trialId;

    private String trialTitle;

    private String trialInsNumber;

    private Integer advanceId;

    private String objectives;

    @Enumerated(EnumType.STRING)
    private SubjectType animalModel;

    @Enumerated(EnumType.STRING)
    private AnimalStudyTypeEnum type;

    private GenericStudyEvaluationDto evaluation;

    private Integer sampleSize; // subjects Total
    private Integer subjectsTotal;
    private Integer subjectsCompleted;
    private Integer subjectsAbandoned;
    private Integer subjectsFailed;
    private Integer maleCount;
    private Integer femaleCount;
    private Integer minAge;
    private Integer maxAge;
    private String ethicalGuidelines;
    private String ethicalGuidelinesUri; // should be a URI

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date endDate;

    // methods
    public void setParentFields(Integer researchId, Integer trialId, Integer advanceId, String trialTitle, String trialInsNumber, Date advanceStartDate, Date advanceEndDate) {
        this.researchId = researchId;
        this.trialId = trialId;
        this.advanceId = advanceId;
        this.trialTitle = trialTitle;
        this.trialInsNumber = trialInsNumber;
        this.startDate = advanceStartDate;
        this.endDate = advanceEndDate;
    }
}
