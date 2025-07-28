package main

import (
	"context"
	"flag"
	pb "github.com/aestebance/example-gRPC/calculatorpb"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials"
	"log"
	"time"
)

var (
	serverAddr   = flag.String("serverAddr", "localhost:50051", "The server address in the format of host:port")
	firstNumber  = flag.Int64("firstNumber", 10, "The first number")
	secondNumber = flag.Int64("secondNumber", 20, "The second number")
)

func main() {
	flag.Parse()

	creds, err := credentials.NewClientTLSFromFile("../../certs/server.crt", "")
	if err != nil {
		log.Fatalf("could not load TLS cert: %v", err)
	}

	conn, err := grpc.Dial(*serverAddr, grpc.WithTransportCredentials(creds))
	if err != nil {
		log.Fatalf("did not connect: %v", err)
	}
	defer conn.Close()

	client := pb.NewCalculatorClient(conn)
	ctx, cancel := context.WithTimeout(context.Background(), time.Second)
	defer cancel()
	addResp, err := client.Add(ctx, &pb.AddRequest{A: *firstNumber, B: *secondNumber})
	if err != nil {
		log.Fatalf("could not greet: %v", err)
	}
	log.Printf("Sum: %d", addResp.GetResult())
	subtractResp, err := client.Subtract(ctx, &pb.SubtractRequest{A: *firstNumber, B: *secondNumber})
	if err != nil {
		log.Fatalf("could not subtract: %v", err)
	}
	log.Printf("Difference: %d", subtractResp.GetResult())
	log.Println("Client finished successfully.")
}
