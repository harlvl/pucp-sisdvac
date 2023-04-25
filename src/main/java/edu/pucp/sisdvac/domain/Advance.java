package edu.pucp.sisdvac.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "advance")
public class Advance {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer subjectsTotal;
    private Integer subjectsCompleted;
    private Integer subjectsAbandoned;
    private Integer subjectsFailed;
    private Integer males;
    private Integer females;
    private Integer minAge;
    private Integer maxAge;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    private Date endDate;
    private String subject; //applies only for Preclinical
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<AdverseEvent> adverseEvents;
}
