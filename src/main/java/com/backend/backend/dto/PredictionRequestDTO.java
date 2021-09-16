package com.backend.backend.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class PredictionRequestDTO {

    private String modelName;
    private String username;
    private String predictionName;
    private Map<String, List<Object>> input;


    public void transformInputsForGRPC(){
        avoidInt64();
    }
    private void avoidInt64(){
        this.input.forEach((k,v)-> {
            AtomicInteger v_index = new AtomicInteger();
            v.forEach(x->{
                if(x instanceof Integer){
                    Double doubleVal = ((Integer) x).doubleValue();
                    v.set(v_index.get(),doubleVal);
                    v_index.getAndIncrement();
                }
            });
        });
    }

}
