package me.park.chatting.rsaspringjava.rsa.service;

import com.google.gson.Gson;
import java.io.FileReader;
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
import me.park.chatting.rsaspringjava.rsa.dto.EmrUserDto;
import me.park.chatting.rsaspringjava.rsa.dto.RsaGenerateKeyRequestDto;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
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

  public EmrUserDto decryptWithPrivateKey(String encryptedData, String base64PrivateKey) throws Exception {

    // BouncyCastle을 보안 프로바이더로 등록
    Security.addProvider(new BouncyCastleProvider());

    // pem 파일로 읽어서 받는 경우
//    String privateKeyPath = ".ssh/private_key.pem"; // 파일 경로 설정
//    PrivateKey privateKey = readPrivateKeyFromPEM(privateKeyPath);

    // base64PrivateKey로 받는 경우
    PrivateKey privateKey = readPrivateKeyFromBase64(base64PrivateKey);

    // RSA 복호화
    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);

    byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
    byte[] decryptedData = cipher.doFinal(encryptedBytes);


    Gson gson = new Gson();
    return gson.fromJson(new String(decryptedData), EmrUserDto.class);
  }

  private PrivateKey readPrivateKeyFromPEM(String filePath) throws Exception {
    try (PEMParser pemParser = new PEMParser(new FileReader(filePath))) {
      Object object = pemParser.readObject();
      System.out.println(object);
      PEMKeyPair pemKeyPair = (PEMKeyPair) object;
      KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
      PrivateKeyInfo privateKeyInfo =pemKeyPair.getPrivateKeyInfo();
      return keyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(privateKeyInfo.getEncoded()));
    }
  }

  private PrivateKey readPrivateKeyFromBase64(String base64PrivateKey) throws Exception {
    // Base64로 인코딩된 private_key 문자열을 디코딩하여 byte[]로 변환
    byte[] decodedKey = Base64.getDecoder().decode(base64PrivateKey);

    // KeyFactory를 사용하여 PrivateKey 객체 생성 (Bouncy Castle을 사용하려면 "BC"를 명시적으로 설정)
    KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);

    return keyFactory.generatePrivate(keySpec);
  }
}
