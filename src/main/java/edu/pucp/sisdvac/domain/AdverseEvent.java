package edu.pucp.sisdvac.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adverse_event")
public class AdverseEvent {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "patient_cid")
    private String patientCID;

//    this will document number for humans and codename for animals
    @Column(name = "subject_identifier")
    private String subjectIdentifier;

    private String description;

    private Integer age;

    private char sex; // F or M

    private Double weight;

    private Double height;
}
