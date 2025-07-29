package com.example.calculator;

import io.grpc.ManagedChannel;
import com.example.calculator.CalculatorProto.AddRequest;
import com.example.calculator.CalculatorProto.AddResponse;
import com.example.calculator.CalculatorProto.SubtractRequest;
import com.example.calculator.CalculatorProto.SubtractResponse;

import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import javax.net.ssl.SSLException;
import java.io.File;

public class CalculatorClient {
    public static void main(String[] args) {
        
        try {
                ManagedChannel channel = NettyChannelBuilder
                        .forAddress("localhost", 50051)
                        .sslContext(GrpcSslContexts.forClient().trustManager(new File("certs/server.crt")).build())
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

                // Construir la petición para resta
                SubtractRequest subtractRequest = SubtractRequest.newBuilder()
                        .setA(30)
                        .setB(15)
                        .build();

                // Llamar al método remoto para resta
                SubtractResponse subtractResponse = stub.subtract(subtractRequest);
                System.out.println("Resta: " + subtractResponse.getResult());

                // Cerrar canal
                channel.shutdown();
        } catch (SSLException e) {
            System.err.println("Error al configurar SSL: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al llamar al servicio: " + e.getMessage());
        }
    }
}