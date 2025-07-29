#include <iostream>
#include <memory>
#include <string>

#include <grpcpp/grpcpp.h>
#include "calculator.grpc.pb.h"

using grpc::Channel;
using grpc::ClientContext;
using grpc::Status;

using calculator::Calculator;
using calculator::AddRequest;
using calculator::AddResponse;
using calculator::SubtractRequest;
using calculator::SubtractResponse;

class CalculatorClient {
public:
    CalculatorClient(std::shared_ptr<Channel> channel)
        : stub_(Calculator::NewStub(channel)) {}

    void Add(int a, int b) {
        AddRequest request;
        request.set_a(a);
        request.set_b(b);

        AddResponse response;
        ClientContext context;

        Status status = stub_->Add(&context, request, &response);

        if (status.ok()) {
            std::cout << "🧮 Suma: " << response.result() << std::endl;
        } else {
            std::cerr << "❌ Error al sumar: " << status.error_message() << std::endl;
        }
    }

    void Subtract(int a, int b) {
        SubtractRequest request;
        request.set_a(a);
        request.set_b(b);

        SubtractResponse response;
        ClientContext context;

        Status status = stub_->Subtract(&context, request, &response);

        if (status.ok()) {
            std::cout << "➖ Resta: " << response.result() << std::endl;
        } else {
            std::cerr << "❌ Error al restar: " << status.error_message() << std::endl;
        }
    }

private:
    std::unique_ptr<Calculator::Stub> stub_;
};

int main() {
    CalculatorClient client(grpc::CreateChannel("localhost:50051", grpc::InsecureChannelCredentials()));
    client.Add(10, 5);
    client.Subtract(20, 7);

    return 0;
}
