using System.Text.Json.Serialization;

namespace RsaApi.Models
{
    public class RsaGenerateKeyRequestDto
    {
        private string publicKey;
        private string privateKey;

        public string getPublicKey(){
            return this.publicKey;
        }

        public string getPrivateKey(){
            return this.privateKey;
        }

        public RsaGenerateKeyRequestDto(string publicKey, string privateKey)
        {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }
    }

    public class RsaDecryptMessageDto
    {
        public string decryptMessage;

        public string getDecryptMessage(){
            return this.decryptMessage;
        }

        public RsaDecryptMessageDto(string decryptMessage)
        {
            this.decryptMessage = decryptMessage;
        }
    }

    public class DecryptMessageRequestDto
    {
        public string ciphertextBase64 { get; set; }
    }
    public class EmrUserDto
    {
        [JsonPropertyName("name")]
        public string Name { get; set; }

        [JsonPropertyName("birthday")]
        public string Birthday { get; set; }

        [JsonPropertyName("gender")]
        public string Gender { get; set; }

        [JsonPropertyName("phone")]
        public string Phone { get; set; }

        [JsonPropertyName("emrUserIdx")]
        public string EmrUserIdx { get; set; }

        public EmrUserDto(string name, string birthday, string gender, string phone, string emrUserIdx)
        {
            this.Name = name;
            this.Birthday = birthday;
            this.Gender = gender;
            this.Phone = phone;
            this.EmrUserIdx = emrUserIdx;
        }
    }
}