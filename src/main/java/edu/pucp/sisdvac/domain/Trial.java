package edu.pucp.sisdvac.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.pucp.sisdvac.domain.enums.Stage;
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
@Table(name = "trial")
public class Trial {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    @Column(name = "ins_number", unique = true, nullable = false)
    private String insNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Stage stage; // clinical or preclinical

    private Integer phase;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @Column(name = "end_date")
    private Date endDate;

    @OneToOne(cascade = CascadeType.ALL)
    private CandidateVaccine candidate;

    @OneToOne(cascade = CascadeType.ALL)
    private TrialStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    private TargetProductProfile targetProductProfile;

    @OneToOne(cascade = CascadeType.ALL)
    private Formulation formulation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Advance> advances;

    // START audit fields
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @Column(name = "created_at")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @Column(name = "last_updated_at")
    private Date lastUpdatedAt;
    // END audit fields

}
