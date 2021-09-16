package com.backend.backend.service;

import com.backend.backend.configuration.GrpcService;
import com.backend.backend.grpc.proto.PredictServiceGrpc;
import com.backend.backend.grpc.proto.PredictionRequest;
import com.backend.backend.grpc.proto.PredictionResponse;
import com.backend.backend.model.PredictionMap;
import com.backend.backend.dto.PredictionRequestDTO;
import com.backend.backend.dto.PredictionResponseDTO;
import com.backend.backend.model.Predictions;
import com.backend.backend.exception.ModelException;
import com.backend.backend.model.Model;
import com.backend.backend.repository.ModelsDAO;
import com.backend.backend.repository.PredictionDAO;
import com.google.gson.Gson;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class PredictionServiceImpl implements PredictionService {

    @Autowired
    GrpcService grpcService;

    @Autowired
    ModelsDAO modelsDAO;

    @Autowired
    PredictionDAO predictionDAO;

    @Override
    public PredictionMap makePrediction(PredictionRequestDTO predictionRequestDTO) throws ModelException {

        Model model = modelsDAO.findByModelName(predictionRequestDTO.getModelName());
        List<String> dependencies = new ArrayList<>();
        model.getDependencies().forEach(x-> dependencies.add(x.getName()));
        if (model != null) {
            ManagedChannel channel = grpcService.getChannel();
            PredictServiceGrpc.PredictServiceBlockingStub stub
                    = grpcService.getStub(channel);

            predictionRequestDTO.transformInputsForGRPC();
            String inputJson = stringJsonFromMap(predictionRequestDTO.getInput());
            PredictionResponse predictionResponse = stub.predict(PredictionRequest.newBuilder()
                    .setModelName(model.getModelName())
                    .setRepositoryName(model.getRepositoryName())
                    .setId(model.getId().toString())
                    .setObjectName(model.getObjectName())
                    .setInput(inputJson)
                    .addAllDependencies(dependencies)
                    .build());

            channel.shutdown();

            PredictionResponseDTO predictionFromEngine = PredictionResponseDTO.builder()
                    .prediction(predictionResponse.getPrediction())
                    .build();


            List<Predictions> prediction = stringJsonToMap(predictionFromEngine.getPrediction());

            PredictionMap output = PredictionMap.builder()
                    .modelName(predictionRequestDTO.getModelName())
                    .predictionName(predictionRequestDTO.getPredictionName())
                    .userName(predictionRequestDTO.getUsername())
                    .date(new Date())
                    .predictionsList(prediction)
                    .build();

            // Store prediction in mongoDB

            predictionDAO.insert(output);




            return output;
        } else {
            throw new ModelException("Model not found");
        }
    }

    @Override
    public List<PredictionMap> getPredictionsByModel(String model) {

        List<PredictionMap> output =  predictionDAO.findByModelName(model);



        return output;
    }

    private String stringJsonFromMap(Map<String, List<Object>> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    private List<Predictions> stringJsonToMap(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, List.class);
    }
}
