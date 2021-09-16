package com.backend.backend.service;

import com.backend.backend.dto.ModelDTO;
import com.backend.backend.exception.ModelException;
import com.backend.backend.model.Model;

import java.io.IOException;
import java.util.List;

public interface ModelService {

    public Model insertModel(ModelDTO model) throws ModelException, IOException;

    public ModelDTO getModel(String name);

    public Boolean deleteModel(String id);

    public Model updateModel(ModelDTO model) throws IOException;

    public List<Model> getAllModels();

}
