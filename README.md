# Example of Google RPC (https://grpc.io/)
## Use
pip install -r requirements.txt

python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. calculator.proto

mkdir certs && cd certs

openssl genrsa -out server.key 2048

openssl req -new -x509 -key server.key -out server.crt -days 365 -subj "/CN=localhost"

python servidor.py

python cliente.py