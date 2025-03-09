import { Injectable } from '@nestjs/common';
import NodeRSA from 'node-rsa';
import fs from 'node:fs';
import { RsaDecryptMessageDto } from './dto/rsaDecryptMessage.dto';
import { EmrUserDto } from './dto/EmrUser.dto';

@Injectable()
export class RsaService {
  public generate = (): { publicKey: string; privateKey: string } => {
    const key = new NodeRSA({ b: 2048 });
    key.setOptions({ encryptionScheme: 'pkcs1_oaep' });
    const publicKey = key.exportKey('pkcs1-public-pem');
    const privateKey = key.exportKey('pkcs1-private-pem');
    return { publicKey, privateKey };
  };

  public decryptMessage = (requestDto: RsaDecryptMessageDto) => {
    const privateKeyPem = fs.readFileSync('./.ssh/private_key.pem', 'utf8');
    const key2 = new NodeRSA(privateKeyPem);
    const decrypted = key2.decrypt(requestDto.decryptMessage, 'utf8');
    // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
    const emrUser: EmrUserDto = JSON.parse(decrypted);
    return emrUser;
  };
}
