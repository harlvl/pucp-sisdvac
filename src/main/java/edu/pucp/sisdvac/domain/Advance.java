package edu.pucp.sisdvac.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.pucp.sisdvac.domain.enums.Stage;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Stage stage; // clinical or preclinical

    // the following 2 fields should be moved into animal study
    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;
    private String subjectName; //applies only for Preclinical

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<AdverseEvent> adverseEvents;


    // START audit fields
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @Column(name = "created_at")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @Column(name = "last_updated_at")
    private Date lastUpdatedAt;
    // END audit fields
}
