#include <iostream>
#include <memory>
#include <string>

#include <grpcpp/grpcpp.h>
#include "calculator.grpc.pb.h"

using grpc::Server;
using grpc::ServerBuilder;
using grpc::ServerContext;
using grpc::Status;

using calculator::AddRequest;
using calculator::AddResponse;
using calculator::SubtractRequest;
using calculator::SubtractResponse;
using calculator::Calculator;

// Implementación del servicio
class CalculatorServiceImpl final : public Calculator::Service {
public:
    Status Add(ServerContext* context, const AddRequest* request, AddResponse* response) override {
        int result = request->a() + request->b();
        response->set_result(result);
        return Status::OK;
    }

    Status Subtract(ServerContext* context, const SubtractRequest* request, SubtractResponse* response) override {
        int result = request->a() - request->b();
        response->set_result(result);
        return Status::OK;
    }
};

void RunServer() {
    std::string server_address("0.0.0.0:50051");
    CalculatorServiceImpl service;

    ServerBuilder builder;
    builder.AddListeningPort(server_address, grpc::InsecureServerCredentials());
    builder.RegisterService(&service);
    std::unique_ptr<Server> server(builder.BuildAndStart());
    std::cout << "🚀 Servidor escuchando en " << server_address << std::endl;

    server->Wait();
}

int main() {
    RunServer();
    return 0;
}
