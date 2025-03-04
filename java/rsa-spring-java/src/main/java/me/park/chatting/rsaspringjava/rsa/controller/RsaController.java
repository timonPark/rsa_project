package me.park.chatting.rsaspringjava.rsa.controller;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import lombok.RequiredArgsConstructor;
import me.park.chatting.rsaspringjava.common.ApiResponse;
import me.park.chatting.rsaspringjava.rsa.dto.DecryptRequestDto;
import me.park.chatting.rsaspringjava.rsa.dto.EncryptRequestDto;
import me.park.chatting.rsaspringjava.rsa.dto.RsaGenerateKeyRequestDto;
import me.park.chatting.rsaspringjava.rsa.service.RsaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("rsa")
@RequiredArgsConstructor
public class RsaController {
  private final RsaService rsaService;

  @PostMapping("/generate")
  public ApiResponse<RsaGenerateKeyRequestDto> generateKey() {
    try {
      return new ApiResponse<RsaGenerateKeyRequestDto>(HttpStatus.OK, "", rsaService.generateKey());
    } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
      throw new RuntimeException(e);
    }
  }

  // 공개키로 암호화
  @PostMapping("/encrypt")
  public ApiResponse<String> encryptData(@RequestBody EncryptRequestDto requestDto) throws Exception {
    return new ApiResponse<String>(HttpStatus.OK, "encrypt successful", rsaService.encryptWithPublicKey(requestDto.getData(), requestDto.getPublicKey()));
  }

  // 개인키로 복호화
  @PostMapping("/decrypt")
  public ApiResponse<String> decryptData(@RequestBody DecryptRequestDto requestDto) throws Exception {
    return new ApiResponse<String>(HttpStatus.OK, "decrypt successful", rsaService.decryptWithPrivateKey(requestDto.getData(), requestDto.getPrivateKey()));
  }
}
