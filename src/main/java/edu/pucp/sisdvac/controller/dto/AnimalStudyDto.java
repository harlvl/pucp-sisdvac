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
public class AnimalStudyDto {
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

    private AnimalStudyEvaluationDto evaluation;

    private Integer sampleSize;

    private String ethicalGuidelines;

    private String ethicalGuidelinesUri; // should be a URI

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date endDate;

    public void setParentFields(Integer researchId, Integer trialId, Integer advanceId, String trialTitle, String trialInsNumber, Date advanceStartDate, Date advanceEndDate) {
        this.researchId = researchId;
        this.trialId = trialId;
        this.advanceId = advanceId;
        this.trialTitle = trialTitle;
        this.trialInsNumber = trialInsNumber;
        this.startDate = advanceStartDate;
        this.endDate = advanceEndDate;
    }

    // study endpoints fields
}
