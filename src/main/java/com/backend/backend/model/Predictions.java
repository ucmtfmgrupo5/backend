package com.backend.backend.model;

import lombok.Data;

import java.util.Map;

@Data
public class Predictions {

    private Map<String, Object> input;
    private String prediction;
}
