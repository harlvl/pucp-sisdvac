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
@Table(name = "target_product_profile")
public class TargetProductProfile {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<TppItem> items;

    @OneToOne(mappedBy = "targetProductProfile")
    private Trial trial;

    // START audit fields
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @Column(name = "created_at")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_INPUT_FORMAT)
    @Column(name = "last_updated_at")
    private Date lastUpdatedAt;
    // END audit fields
}
