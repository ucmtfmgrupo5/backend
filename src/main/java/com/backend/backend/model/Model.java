package com.backend.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "models")
public class Model extends EntityWithUUID implements Serializable {

    @Column(name = "name", unique = true)
    private String modelName;
    @Column(name = "object_name")
    private String objectName;
    @Column(name = "repository_name")
    private String repositoryName;
    @Column(name = "description")
    private String modelDescription;

    @OneToMany(mappedBy="mod", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Dependency> dependencies = new ArrayList<>();
}
