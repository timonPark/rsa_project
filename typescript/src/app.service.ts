import { Injectable } from '@nestjs/common';
import NodeRSA from 'node-rsa';
import * as fs from 'node:fs';

@Injectable()
export class AppService {
  getHello(): { publicKey: string, privateKey: string } {
    // const key = new NodeRSA({b: 2048});
    // key.setOptions({encryptionScheme : "pkcs1_oaep"})
    // const publicKey = key.exportKey('pkcs1-public-pem');
    // const privateKey = key.exportKey('pkcs1-private-pem');
    //
    // console.log(publicKey);
    // console.log(privateKey);
    // const text = 'Hello RSA!';
    // const encrypted = key.encrypt(text, 'base64');
    // console.log('encrypted: ', encrypted);
    // const decrypted = key.decrypt(encrypted, 'utf8');
    // console.log('decrypted: ', decrypted);

    const publicKeyPem = fs.readFileSync('./.ssh/public_key.pem', 'utf8');
    const privateKeyPem = fs.readFileSync('./.ssh/private_key.pem', 'utf8');
    const key = new NodeRSA(publicKeyPem);
    const key2 = new NodeRSA(privateKeyPem);
    const publicKey = key.exportKey('public');
    const privateKey = key2.exportKey('private');

    console.log('Public Key:', publicKey);
    console.log(privateKey);
    const text = 'Hello RSA!';
    const encrypted = key.encrypt(text, 'base64');
    console.log('encrypted: ', encrypted);
    const decrypted = key2.decrypt(encrypted, 'utf8');
    console.log('decrypted: ', decrypted);
    return {publicKey, privateKey};
  }
}
