package edu.pucp.sisdvac.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.pucp.sisdvac.domain.enums.Stage;
import edu.pucp.sisdvac.utils.Constants;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrialDto {
    private Integer id;
    @NotNull
    private String title;
    @NotNull
    private String insNumber;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Stage stage; // clinical or preclinical
    private Integer phase;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @NotNull
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date endDate;

    private TrialStatusDto status;
}
