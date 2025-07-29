package com.example.calculator;

import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;

public class CalculatorServer {
    public static void main(String[] args) throws Exception {
        Server server = NettyServerBuilder.forPort(50051)
                .useTransportSecurity(
                        new java.io.File("certs/server.crt"),
                        new java.io.File("certs/server.key"))
                .addService(new CalculatorServiceImpl())
                .build();

        System.out.println("Server started on port 50051");
        server.start();
        server.awaitTermination();
    }
}
