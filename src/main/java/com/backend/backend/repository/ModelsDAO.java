package com.backend.backend.repository;

import com.backend.backend.model.Model;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ModelsDAO extends CrudRepository<Model, UUID> {
    Model findByModelName(String name);
}
