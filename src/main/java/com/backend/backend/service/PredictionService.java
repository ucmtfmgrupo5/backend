package com.backend.backend.service;

import com.backend.backend.model.PredictionMap;
import com.backend.backend.dto.PredictionRequestDTO;
import com.backend.backend.exception.ModelException;

import java.util.List;

public interface PredictionService {

        public PredictionMap makePrediction(PredictionRequestDTO predictionRequestDTO) throws ModelException;

        public List<PredictionMap> getPredictionsByModel(String model);
}
