package com.backend.backend.repository;

import com.backend.backend.model.JsonSchema;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JsonSchemaDAO extends MongoRepository<JsonSchema, String> {
    JsonSchema findByModelName(String name);
}
