package edu.pucp.sisdvac.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.pucp.sisdvac.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResearchDto {
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private String insNumber;

    private Collection<UserDto> users;
    private Collection<TrialDto> trials;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @NotNull
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date endDate;
}
