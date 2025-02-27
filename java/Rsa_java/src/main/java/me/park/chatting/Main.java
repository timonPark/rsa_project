package me.park.chatting;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.util.Base64;

public class Main {
  public static void main(String[] args) {
    try {
      // BouncyCastle을 JCE 프로바이더로 추가
      // RSA 개인키 (PEM 형식으로 제공된 값)
      Security.addProvider(new BouncyCastleProvider());

      // 개인키 파일 경로
      String privateKeyPath = "private_key.pem"; // 파일 경로 설정

      // PEM 형식의 개인 키 읽기
      PrivateKey privateKey = readPrivateKeyFromPEM(privateKeyPath);

      // 암호화된 메시지
      String encryptedMessage = "qBskzQF3DCVSLCoCBLwBKNh07jSHJ6zcf72pOo6vMeHstKPx0V7V10hGStJduXvowQMGl+ULJQb6fbcNWQeC4P6vV3CcbWal6zsKDaucKGSbluLVHJdH4Vhb+K/AwZZ9I98gx7WIk6FgJ24C/49C8ewBcOe0LIHq4j+pBpV/4Iup453WBdPuqW/WGbJOFFQ8eyQpnnjZUVHzLj63QPXzdM9d6XRJYA5OIe1sIQgBZUguI1KMtyIUZkSCXzFrZLuftmW7PDVdLDU8F4aXDNhOI7fPoZ6nKu03cV7IK7yVMBGRg+eVIccc35LKgCexIHtzm8YpjVQSFHQyhftgiYXC2w==";

      // 개인키를 사용하여 복호화
      Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
      byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
      String decryptedMessage = new String(decryptedBytes);
      System.out.println(decryptedMessage);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // PEM 파일에서 개인키 읽기 (PKCS#1, PKCS#8 지원)
  public static PrivateKey readPrivateKeyFromPEM(String filePath) throws Exception {
    try (PEMParser pemParser = new PEMParser(new FileReader(filePath))) {
      Object object = pemParser.readObject();
      System.out.println(object);
      PEMKeyPair pemKeyPair = (PEMKeyPair) object;


      KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
      PrivateKeyInfo privateKeyInfo =pemKeyPair.getPrivateKeyInfo();
      return keyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(privateKeyInfo.getEncoded()));
    }
  }
}
