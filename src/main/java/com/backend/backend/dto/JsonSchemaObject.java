package com.backend.backend.dto;

import lombok.Data;

@Data
public class JsonSchemaObject {
    private String key;
    private String type;
    private TemplateOptions templateOptions;

}
