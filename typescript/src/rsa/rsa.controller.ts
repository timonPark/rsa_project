import { Body, Controller, Post } from '@nestjs/common';
import { RsaService } from './rsa.service';
import { RsaDecryptMessageDto } from './dto/rsaDecryptMessage.dto';
import { EmrUserDto } from './dto/EmrUser.dto';

@Controller('rsa')
export class RsaController {
  constructor(private readonly rsaService: RsaService) {}

  @Post('generate')
  public generate(): { publicKey: string; privateKey: string } {
    return this.rsaService.generate();
  }

  @Post('decryptMessage')
  public decryptMessage(
    @Body() rsaDecryptMessage: RsaDecryptMessageDto,
  ): EmrUserDto {
    return this.rsaService.decryptMessage(rsaDecryptMessage);
  }
}
