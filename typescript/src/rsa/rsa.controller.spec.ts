import { Test, TestingModule } from '@nestjs/testing';
import { RsaController } from './rsa.controller';

describe('RsaController', () => {
  let controller: RsaController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [RsaController],
    }).compile();

    controller = module.get<RsaController>(RsaController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
