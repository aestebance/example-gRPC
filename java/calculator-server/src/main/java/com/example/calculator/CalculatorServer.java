package com.example.calculator;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.example.calculator.CalculatorGrpc;
import com.example.calculator.CalculatorProto.AddRequest;
import com.example.calculator.CalculatorProto.AddResponse;

public class CalculatorServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService(new CalculatorServiceImpl())
                .build();

        System.out.println("Server started on port 50051");
        server.start();
        server.awaitTermination();
    }
}
