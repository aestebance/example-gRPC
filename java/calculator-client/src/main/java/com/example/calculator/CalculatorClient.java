package com.example.calculator;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.calculator.CalculatorGrpc;
import com.example.calculator.CalculatorProto.AddRequest;
import com.example.calculator.CalculatorProto.AddResponse;

public class CalculatorClient {
    public static void main(String[] args) {
        // Crear canal sin TLS
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext() // Necesario si el servidor no usa certificados
                .build();

        // Crear stub bloqueante
        CalculatorGrpc.CalculatorBlockingStub stub = CalculatorGrpc.newBlockingStub(channel);

        // Construir la petición
        AddRequest request = AddRequest.newBuilder()
                .setA(10)
                .setB(20)
                .build();

        // Llamar al método remoto
        AddResponse response = stub.add(request);
        System.out.println("Suma: " + response.getResult());

        // Cerrar canal
        channel.shutdown();
    }
}