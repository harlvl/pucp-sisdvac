package edu.pucp.sisdvac.domain;

import edu.pucp.sisdvac.domain.enums.DocumentType;
import edu.pucp.sisdvac.domain.enums.Status;
import edu.pucp.sisdvac.domain.enums.SubjectType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_subject")
public class TestSubject {
    @Id
    @GeneratedValue
    private Integer id;
    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;
    @Column(unique = true, nullable = false)
    private String codeName;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
}
