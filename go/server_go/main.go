package main

import (
	"context"
	"flag"
	"fmt"
	pb "github.com/aestebance/example-gRPC/calculatorpb"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials"
	"log"
	"net"
)

var (
	port = flag.Int("port", 50051, "The server port")
)

type server struct {
	pb.UnimplementedCalculatorServer
}

func (s *server) Add(_ context.Context, in *pb.AddRequest) (*pb.AddResponse, error) {
	log.Printf("Received: A=%d, B=%d", in.GetA(), in.GetB())
	result := in.GetA() + in.GetB()
	return &pb.AddResponse{Result: result}, nil
}

func (s *server) Subtract(_ context.Context, in *pb.SubtractRequest) (*pb.SubtractResponse, error) {
	log.Printf("Received: A=%d, B=%d", in.GetA(), in.GetB())
	result := in.GetA() - in.GetB()
	return &pb.SubtractResponse{Result: result}, nil
}

func main() {
	flag.Parse()

	creds, err := credentials.NewServerTLSFromFile("../../certs/server.crt", "../../certs/server.key")

	if err != nil {
		log.Fatalf("failed to load TLS credentials: %v", err)
	}

	lis, err := net.Listen("tcp", ":"+fmt.Sprint(*port))
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}

	s := grpc.NewServer(grpc.Creds(creds))
	pb.RegisterCalculatorServer(s, &server{})

	log.Printf("gRPC server listening on port %d...", *port)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
