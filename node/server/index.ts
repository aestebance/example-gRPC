import * as grpc from '@grpc/grpc-js';
import * as protoLoader from '@grpc/proto-loader';
import path from 'path';
import * as fs from 'fs';

const PROTO_PATH = path.join(__dirname, '../protos/calculator.proto');
const packageDefinition = protoLoader.loadSync(PROTO_PATH);

const key = fs.readFileSync('../../certs/server.key');
const cert = fs.readFileSync('../../certs/server.crt');

const creds = grpc.ServerCredentials.createSsl(null, [{
  private_key: key,
  cert_chain: cert,
}], false);

const proto = grpc.loadPackageDefinition(packageDefinition) as any;

function add(call: grpc.ServerUnaryCall<{ a: number; b: number }, { result: number }>, callback: grpc.sendUnaryData<{ result: number }>): void {
    const result = call.request.a + call.request.b;
    callback(null, { result });
}

function subtract(call: grpc.ServerUnaryCall<{ a: number; b: number }, { result: number }>, callback: grpc.sendUnaryData<{ result: number }>): void {
    const result = call.request.a - call.request.b;
    callback(null, { result });
}

const server = new grpc.Server();
server.addService(proto.calculator.Calculator.service, {add, subtract});

async function main() {
  await new Promise<void>((resolve, reject) => {
    server.bindAsync('0.0.0.0:50051', creds, (err, port) => {
      if (err) return reject(err);
      console.log(`🚀 Server is running on port ${port}`);
      resolve();
    });
  });
}

main();