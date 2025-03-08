using System.Security.Cryptography;
using System.Text.Json;
using RsaApi.Models;

namespace RsaApi.Services
{
    public class RsaService : IRsaService
    {
        private readonly ILogger<RsaService> _logger;

        // 생성자에서 ILogger를 주입받습니다.
        public RsaService(ILogger<RsaService> logger)
        {
            _logger = logger;
        }
        public RsaGenerateKeyRequestDto generateKey()
        {
            _logger.LogInformation("Key generation started.");
            // RSA 객체를 플랫폼에 맞게 생성
            using (var rsa = RSA.Create())
            {
                // 키 길이를 2048 비트로 설정
                rsa.KeySize = 2048;

                // 공개키와 개인키 추출
                var publicKey = rsa.ExportRSAPublicKey();
                var privateKey = rsa.ExportRSAPrivateKey();

                // Base64로 인코딩
                var publicKeyBase64 = Convert.ToBase64String(publicKey);
                var privateKeyBase64 = Convert.ToBase64String(privateKey);

                _logger.LogInformation(publicKeyBase64);
                _logger.LogInformation(privateKeyBase64);
                // DTO 리턴
                return new RsaGenerateKeyRequestDto(publicKeyBase64, privateKeyBase64);
            }
        }

        public string Encrypt(string plaintext, string publicKeyBase64)
        {
            using (var rsa = RSA.Create())
            {
                // 공개키 로드
                rsa.ImportRSAPublicKey(Convert.FromBase64String(publicKeyBase64), out _);

                // OAEP 패딩 방식 적용
                var encryptedBytes = rsa.Encrypt(System.Text.Encoding.UTF8.GetBytes(plaintext), RSAEncryptionPadding.OaepSHA1);

                return Convert.ToBase64String(encryptedBytes);
            }
        }

        public EmrUserDto Decrypt(string ciphertextBase64)
        {
            using (var rsa = RSA.Create())
            {
                _logger.LogInformation(ciphertextBase64);
                // 루트 경로에 있는 개인키 파일 경로 지정
                string privateKeyFilePath = Path.Combine(Directory.GetCurrentDirectory(), "private_key.pem");

                // 개인키를 파일에서 읽어서 Base64로 변환
                string privateKeyBase64 = ReadPrivateKeyFromFile(privateKeyFilePath);

                // Base64를 디코딩하여 RSA 개인키 로드
                rsa.ImportRSAPrivateKey(Convert.FromBase64String(privateKeyBase64), out _);

                // OAEP 패딩 방식 적용
                byte[] decryptedBytes = rsa.Decrypt(Convert.FromBase64String(ciphertextBase64), RSAEncryptionPadding.OaepSHA1);

                string result = System.Text.Encoding.UTF8.GetString(decryptedBytes);
                _logger.LogInformation(result);
                var options = new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true // 대소문자 구분을 하지 않도록 설정
                };

                try
                {
                    // JSON 문자열을 EmrUserDto 객체로 역직렬화
                    return JsonSerializer.Deserialize<EmrUserDto>(result) ?? new EmrUserDto("", "", "", "", "");
                }
                catch (JsonException)
                {
                    // 역직렬화 실패 시 기본값을 반환
                    Console.WriteLine("Error during deserialization. Returning default EmrUserDto.");
                    return new EmrUserDto("", "", "", "", "");
                }
            }
        }

        private string ReadPrivateKeyFromFile(string filePath)
        {
            // 개인키 파일을 읽음
            string privateKey = File.ReadAllText(filePath);
            
            // PEM 형식에서 불필요한 부분 제거 (예: "-----BEGIN PRIVATE KEY-----"와 "-----END PRIVATE KEY-----" 부분)
            privateKey = privateKey.Replace("-----BEGIN RSA PRIVATE KEY-----", "")
                                .Replace("-----END RSA PRIVATE KEY-----", "")
                                .Replace("\n", "")
                                .Replace("\r", "");
            return privateKey;
        }
    }
}
