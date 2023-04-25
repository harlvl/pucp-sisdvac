package edu.pucp.sisdvac.domain;

import edu.pucp.sisdvac.domain.enums.DocumentType;
import edu.pucp.sisdvac.domain.enums.Status;
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
@Table(name = "test_volunteer")
public class TestVolunteer {
    @Id
    @GeneratedValue
    private Integer id;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @Column(unique = true)
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
}
