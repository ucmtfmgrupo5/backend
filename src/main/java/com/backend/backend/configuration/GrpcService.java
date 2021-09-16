package com.backend.backend.configuration;

import com.backend.backend.grpc.proto.PredictServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "grpc")
@Data
public class GrpcService {

    private ManagedChannel channel;
    private PredictServiceGrpc.PredictServiceBlockingStub stub;
    private Integer port;
    private String host;

    public ManagedChannel getChannel(){
        return ManagedChannelBuilder.forAddress(this.host, this.port)
                .usePlaintext()
                .build();
    }

    public PredictServiceGrpc.PredictServiceBlockingStub getStub(ManagedChannel channel){
        return PredictServiceGrpc.newBlockingStub(channel);
    }


}
