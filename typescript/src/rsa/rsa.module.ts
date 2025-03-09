import { Module } from '@nestjs/common';
import { RsaService } from './rsa.service';
import { RsaController } from './rsa.controller';

@Module({
  providers: [RsaService],
  controllers: [RsaController]
})
export class RsaModule {}
