import * as grpc from '@grpc/grpc-js';
import * as protoLoader from '@grpc/proto-loader';
import path from 'path';
import * as fs from 'fs';

const PROTO_PATH = path.join(__dirname, '../protos/calculator.proto');
const packageDefinition = protoLoader.loadSync(PROTO_PATH);
const proto = grpc.loadPackageDefinition(packageDefinition) as any;

const rootCert = fs.readFileSync('../../certs/server.crt');
const creds = grpc.credentials.createSsl(rootCert);

const client = new proto.calculator.Calculator(
  'localhost:50051',
  creds
);

client.add({ a: 10, b: 5 }, (err: any, response: any) => {
  if (err) return console.error(err);
  console.log('🧮 Add result:', response.result);

  client.subtract({ a: 10, b: 3 }, (err: any, response: any) => {
    if (err) return console.error(err);
    console.log('➖ Subtract result:', response.result);

    client.close();
  });
});

