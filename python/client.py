import grpc
import calculator_pb2
import calculator_pb2_grpc

def run():
    with open('../certs/server.crt', 'rb') as f:
        trusted_certs = f.read()
    
    credentials = grpc.ssl_channel_credentials(root_certificates=trusted_certs)
    channel = grpc.secure_channel('localhost:50051', credentials)
    stub = calculator_pb2_grpc.CalculatorStub(channel)
    
    response = stub.Add(calculator_pb2.AddRequest(a=7, b=3))
    print(f"Result of the sum: {response.result}")
    response = stub.Subtract(calculator_pb2.SubtractRequest(a=10, b=4))
    print(f"Result of the subtraction: {response.result}")

if __name__ == '__main__':
    run()
