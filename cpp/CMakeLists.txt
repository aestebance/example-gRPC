cmake_minimum_required(VERSION 3.13)
project(calculator-grpc LANGUAGES CXX)

find_package(Protobuf REQUIRED)
find_package(gRPC REQUIRED)

include_directories(
  ${Protobuf_INCLUDE_DIRS}
  ${CMAKE_CURRENT_BINARY_DIR}
)

# Ejecutable del servidor
add_executable(server
  server.cpp
  calculator.pb.cc
  calculator.grpc.pb.cc
)

target_link_libraries(server
  gRPC::grpc++
  protobuf::libprotobuf
)

# Ejecutable del cliente
add_executable(client
  client.cpp
  calculator.pb.cc
  calculator.grpc.pb.cc
)

target_link_libraries(client
  gRPC::grpc++
  protobuf::libprotobuf
)
