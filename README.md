# Example of Google RPC (https://grpc.io/)

## Creating credentials

openssl genrsa -out server.key 2048

openssl req -x509 -new -nodes -key server.key -sha256 -days 365 -out server.crt -config openssl.cnf

## Use for Python
pip install -r requirements.txt

python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. calculator.proto

python server.py

python client.py

## Use for Go
cd go/calculatorpb

protoc --go_out=. --go_opt=paths=source_relative --go-grpc_out=. --go-grpc_opt=paths=source_relative calculator.proto

cd ..

go get google.golang.org/grpc

go get google.golang.org/grpc/credentials/insecure

cd client_go

go build

cd ..
cd server_go

go build

cd ..

./server_go/server_go &
./client_go/client_go


## CPP
git clone --recurse-submodules -b v1.64.0 https://github.com/grpc/grpc
cd grpc
mkdir -p cmake/build
cd cmake/build
cmake ../.. -DCMAKE_INSTALL_PREFIX=/usr/local -DgRPC_INSTALL=ON -DgRPC_BUILD_TESTS=OFF
make -j$(nproc)
sudo make install
mkdir build && cd build
protoc -I .. --grpc_out=. --plugin=protoc-gen-grpc=`which grpc_cpp_plugin` ../calculator.proto
protoc -I .. --cpp_out=. ../calculator.proto
cmake ..
 make -j$(nproc)