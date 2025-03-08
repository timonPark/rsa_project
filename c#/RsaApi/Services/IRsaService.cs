using RsaApi.Models;

namespace RsaApi.Services{
    public interface IRsaService
    {
        RsaGenerateKeyRequestDto generateKey();
    }
}