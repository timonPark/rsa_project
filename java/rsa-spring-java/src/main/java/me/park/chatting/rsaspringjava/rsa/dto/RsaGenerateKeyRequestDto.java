package me.park.chatting.rsaspringjava.rsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RsaGenerateKeyRequestDto {
  private String publicKey;
  private String privateKey;
}
