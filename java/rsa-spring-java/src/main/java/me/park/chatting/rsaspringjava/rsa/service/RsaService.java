package me.park.chatting.rsaspringjava.rsa.service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import me.park.chatting.rsaspringjava.rsa.dto.RsaGenerateKeyRequestDto;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

@Service
public class RsaService {

  public RsaGenerateKeyRequestDto generateKey()
      throws NoSuchAlgorithmException, NoSuchProviderException {
    Security.addProvider(new BouncyCastleProvider());

    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
    keyPairGenerator.initialize(2048);

    KeyPair keyPair = keyPairGenerator.generateKeyPair();

    PublicKey publicKey = keyPair.getPublic();
    PrivateKey privateKey = keyPair.getPrivate();

    RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
    RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
    return new RsaGenerateKeyRequestDto(
        Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded()),
        Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded())
    );
  }

  public String encryptWithPublicKey(String data, String base64PublicKey) throws Exception {
    // Base64로 인코딩된 공개키 문자열을 디코딩하여 PublicKey 객체로 변환
    byte[] decodedPublicKey = Base64.getDecoder().decode(base64PublicKey);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedPublicKey);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PublicKey publicKey = keyFactory.generatePublic(keySpec);

    // RSA 암호화
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);

    byte[] encryptedData = cipher.doFinal(data.getBytes());

    // 결과를 Base64로 인코딩하여 반환
    return Base64.getEncoder().encodeToString(encryptedData);
  }

  public String decryptWithPrivateKey(String encryptedData, String base64PrivateKey) throws Exception {
    // Base64로 인코딩된 개인키 문자열을 디코딩하여 PrivateKey 객체로 변환
    byte[] decodedPrivateKey = Base64.getDecoder().decode(base64PrivateKey);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedPrivateKey);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

    // RSA 복호화
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);

    byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
    byte[] decryptedData = cipher.doFinal(encryptedBytes);

    return new String(decryptedData);
  }
}
