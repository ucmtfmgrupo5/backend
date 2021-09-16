package com.backend.backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PredictionResponseDTO {
    private String prediction;
}
