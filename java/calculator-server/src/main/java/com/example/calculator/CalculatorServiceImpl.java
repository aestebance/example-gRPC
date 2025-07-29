package com.example.calculator;

import io.grpc.stub.StreamObserver;
import com.example.calculator.CalculatorProto.AddRequest;
import com.example.calculator.CalculatorProto.AddResponse;
import com.example.calculator.CalculatorProto.SubtractRequest;
import com.example.calculator.CalculatorProto.SubtractResponse;

public class CalculatorServiceImpl extends CalculatorGrpc.CalculatorImplBase {
    @Override
    public void add(AddRequest request, StreamObserver<AddResponse> responseObserver) {
        int result = request.getA() + request.getB();
        AddResponse response = AddResponse.newBuilder().setResult(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void subtract(SubtractRequest request, StreamObserver<SubtractResponse> responseObserver) {
        int result = request.getA() - request.getB();
        SubtractResponse response = SubtractResponse.newBuilder().setResult(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}