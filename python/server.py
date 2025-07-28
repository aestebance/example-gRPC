from concurrent import futures
import grpc
import calculator_pb2
import calculator_pb2_grpc

class ServerCalculator(calculator_pb2_grpc.CalculatorServicer):
    def Add(self, request, context):
        result = request.a + request.b
        return calculator_pb2.AddResponse(result=result)
    
    def Subtract(self, request, context):
        result = request.a - request.b
        return calculator_pb2.SubtractResponse(result=result)

def serve():
    with open('../certs/server.key', 'rb') as f:
        private_key = f.read()
    with open('../certs/server.crt', 'rb') as f:
        certificate_chain = f.read()
    credentials = grpc.ssl_server_credentials([(private_key, certificate_chain)])
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    calculator_pb2_grpc.add_CalculatorServicer_to_server(ServerCalculator(), server)
    server.add_secure_port('[::]:50051', credentials)
    server.start()
    print("gRPC server listening in port 50051...")
    server.wait_for_termination()

if __name__ == '__main__':
    serve()
