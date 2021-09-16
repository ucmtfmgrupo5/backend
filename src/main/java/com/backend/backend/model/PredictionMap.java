package com.backend.backend.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Document
public class PredictionMap {

    @Id
    private String id;
    private String modelName;
    private String predictionName;
    private List<Predictions> predictionsList;
    private String userName;
    private Date date;

}
