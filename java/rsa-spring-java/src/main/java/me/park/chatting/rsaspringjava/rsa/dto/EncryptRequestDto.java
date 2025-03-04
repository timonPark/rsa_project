package me.park.chatting.rsaspringjava.rsa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncryptRequestDto {
  private String data;      // 암호화할 데이터
  private String publicKey; // Base64로 인코딩된 공개키
}
