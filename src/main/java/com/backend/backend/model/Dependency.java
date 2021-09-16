package com.backend.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dependencies")
public class Dependency extends EntityWithUUID implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="model", nullable=false)
    @JsonBackReference
    private Model mod;
    @Column(name = "name")
    private String name;

}
