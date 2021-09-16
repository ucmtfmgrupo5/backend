package com.backend.backend.dto;

import com.backend.backend.model.Dependency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ModelDTO {

    private UUID id;
    private String modelName;
    private String objectName;
    private String repositoryName;
    private String modelDescription;
    private MultipartFile file;
    private List<JsonSchemaObject> jsonSchema;
    private List<Dependency> dependencyList;
}
