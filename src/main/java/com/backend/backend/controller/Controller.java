package com.backend.backend.controller;

import com.backend.backend.dto.ModelDTO;
import com.backend.backend.model.PredictionMap;
import com.backend.backend.dto.PredictionRequestDTO;
import com.backend.backend.model.Model;
import com.backend.backend.service.ModelService;
import com.backend.backend.service.PredictionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("controller")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@Slf4j
public class Controller {

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private ModelService modelService;


    @PostMapping(value = "/predict", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity predict(@RequestBody PredictionRequestDTO body) {
        log.info("Entering prediction Controller");
        try {
            PredictionMap output = predictionService.makePrediction(body);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/getPredictions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity predict(@RequestParam String model) {
        log.info("Entering getPredictions by model Controller");
        try {
            List<PredictionMap> output = predictionService.getPredictionsByModel(model);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/insertModel")
    @ResponseBody
    public ResponseEntity insertModel(@ModelAttribute ModelDTO body) {
        log.info("Entering insertModel Controller");
        try {
            Model output = modelService.insertModel(body);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/getModel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getModel(@RequestParam String name) {
        log.info("Entering getModel Controller");
        try {
            ModelDTO output = modelService.getModel(name);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/getModels", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getModels() {
        log.info("Entering getModels Controller");
        try {
            List<Model> output = modelService.getAllModels();
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/updateModel")
    @ResponseBody
    public ResponseEntity updateModel(@ModelAttribute ModelDTO model) {
        log.info("Entering updateModel Controller");
        try {
            Model output = modelService.updateModel(model);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/deleteModel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deleteModel(@RequestParam String id) {
        log.info("Entering updateModel Controller");
        try {
            Boolean output = modelService.deleteModel(id);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}