package com.backend.backend.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;

@Document(collection = "jsonShema")
@Data
@Builder
public class JsonSchema {

    @Id
    private String id;

    private String modelName;

    private byte[] jsonFile;

}
