package me.park.chatting.rsaspringjava.rsa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecryptRequestDto {
  private String data; // 암호화된 데이터
  private String privateKey;    // Base64로 인코딩된 개인키
}
