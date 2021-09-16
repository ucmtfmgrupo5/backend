package com.backend.backend.repository;

import com.backend.backend.model.PredictionMap;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PredictionDAO extends MongoRepository<PredictionMap, String> {
    List<PredictionMap> findByModelName(String name);
}
