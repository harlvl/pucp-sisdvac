package edu.pucp.sisdvac.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import edu.pucp.sisdvac.domain.enums.FormulationStatus;
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
@Table(name = "vaccine_formulation")
public class Formulation {
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private FormulationStatus status;

    @Column(name = "_order") // make sure this column is not called just "order" or it will throw a huge fucking error
    private Integer order; // used to keep track of which formulation was first

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<FormulationItem> items;

    // START audit fields
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @Column(name = "created_at")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @Column(name = "last_updated_at")
    private Date lastUpdatedAt;
    // END audit fields
}
