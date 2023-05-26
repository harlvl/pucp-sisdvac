package edu.pucp.sisdvac.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.pucp.sisdvac.domain.enums.Stage;
import edu.pucp.sisdvac.domain.enums.SubjectType;
import edu.pucp.sisdvac.utils.Constants;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class AdvanceDto {
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer subjectsTotal;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer subjectsCompleted;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer subjectsAbandoned;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer subjectsFailed;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer males;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer females;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer minAge;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer maxAge;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private Stage stage; // clinical or preclinical

    private AnimalStudyDto animalStudy; // will only exist if stage is preclinical

    // the following 2 fields should be moved into animal study
    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;
    private String subjectName; //applies only for Preclinical

    private Collection<AdverseEventDto> adverseEvents;

    // START audit fields
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date lastUpdatedAt;
    // END audit fields

}
