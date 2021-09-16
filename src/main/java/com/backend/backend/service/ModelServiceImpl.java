package com.backend.backend.service;


import com.backend.backend.dto.JsonSchemaObject;
import com.backend.backend.dto.ModelDTO;
import com.backend.backend.exception.ModelException;
import com.backend.backend.model.Dependency;
import com.backend.backend.model.JsonSchema;
import com.backend.backend.model.Model;
import com.backend.backend.repository.JsonSchemaDAO;
import com.backend.backend.repository.ModelsDAO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    ModelsDAO modelsDAO;

    @Autowired
    JsonSchemaDAO jsonSchemaDAO;


    @Override
    public Model insertModel(ModelDTO model) throws ModelException, IOException {
        Model modelEntity = Model.builder()
                .modelName(model.getModelName())
                .objectName(model.getObjectName())
                .repositoryName(model.getRepositoryName())
                .modelDescription(model.getModelDescription())
                .build();
        if(model.getDependencyList()!=null){
            buildDependencies(modelEntity, model);
        }
        JsonSchema schema = JsonSchema.builder()
                .jsonFile(model.getFile().getBytes())
                .modelName(model.getModelName())
                .build();
        Model storedModel = null;
        JsonSchema storedSchema = null;
        try{

            storedModel = modelsDAO.save(modelEntity);
            schema.setId(storedModel.getId().toString());
            storedSchema = jsonSchemaDAO.insert(schema);
            return storedModel;
        }catch(Exception e){
            if(storedModel!=null){
                modelsDAO.delete(modelEntity);
            }
            if(storedSchema!=null){
                jsonSchemaDAO.delete(storedSchema);
            }

            throw new ModelException("Model cant be stored, please check data provided");
        }
    }

    @Override
    public ModelDTO getModel(String name) {
        Model model = modelsDAO.findByModelName(name);
        JsonSchema schema = jsonSchemaDAO.findByModelName(name);
        String jsonSchema = new String(schema.getJsonFile(), StandardCharsets.UTF_8);

        Gson gson = new Gson();
        List<JsonSchemaObject> schemaObject = gson.fromJson(jsonSchema,List.class);

        return ModelDTO.builder()
                .jsonSchema(schemaObject)
                .id(model.getId())
                .modelName(model.getModelName())
                .objectName(model.getObjectName())
                .dependencyList(model.getDependencies())
                .repositoryName(model.getRepositoryName())
                .modelDescription(model.getModelDescription())
                .build();
    }

    @Override
    public Boolean deleteModel(String id) {
        try {

            modelsDAO.deleteById(UUID.fromString(id));
            jsonSchemaDAO.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Model updateModel(ModelDTO model) throws IOException {
        Optional<Model> modelFromdB = modelsDAO.findById(model.getId());
        Model modelToUpdate = updateModelObject(modelFromdB.get(), model);



        Optional<JsonSchema> schema = jsonSchemaDAO.findById(model.getId().toString());
        if(schema.isPresent()){
            schema.get().setModelName(model.getModelName());
            schema.get().setJsonFile(!model.getFile().isEmpty()?model.getFile().getBytes():schema.get().getJsonFile());
            jsonSchemaDAO.save(schema.get());
        }
        return modelsDAO.save(modelToUpdate);
    }

    @Override
    public List<Model> getAllModels() {
        return (List<Model>) modelsDAO.findAll();
    }

    private Model updateModelObject(Model entity, ModelDTO model) {

        entity.setModelName(model.getModelName());
        entity.setRepositoryName(model.getRepositoryName());
        entity.setObjectName(model.getObjectName());
        entity.setModelDescription(model.getModelDescription());
        buildDependenciesForUpdate(entity,model);
        return entity;
    }

    private void buildDependencies(Model model, ModelDTO body) {
        List<Dependency> aux = new ArrayList<>();
        Model auxMod = Model.builder().build();
        auxMod.setId(model.getId());

        body.getDependencyList().forEach(x -> {
            aux.add(Dependency.builder()
                    .name(x.getName())
                    .mod(auxMod)
                    .build());
        });

        model.setDependencies(aux);
    }

    private void buildDependenciesForUpdate(Model model, ModelDTO body) {
        List<Dependency> aux = new ArrayList<>();
        Model auxMod = Model.builder().build();
        auxMod.setId(model.getId());

        body.getDependencyList().forEach(x -> {
            Dependency dependency = Dependency.builder()
                    .name(x.getName())
                    .mod(auxMod)
                    .build();
            dependency.setId(x.getId());
            aux.add(dependency);
        });

        model.setDependencies(aux);
    }
}
