package edu.pucp.sisdvac.domain;

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
@Table(name = "infectious_disease")
public class InfectiousDisease {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;
    private String source; // should be a website

    private String realm;
    private String variant;
}
