package edu.pucp.sisdvac.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

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
    private Integer subjectsCompleted;
    private Integer subjectsAbandoned;
    private Integer subjectsFailed;
    private Integer males;
    private Integer females;
    private Integer minAge;
    private Integer maxAge;
    private Long startDate;
    private Long endDate;
    private String subject; //applies only for Preclinical
    @OneToMany(fetch = FetchType.LAZY)
    private Collection<AdverseEvent> adverseEvents;
}
