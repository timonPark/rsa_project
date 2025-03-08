using Microsoft.AspNetCore.Mvc;
using RsaApi.Models;
using RsaApi.Services;

namespace RsaApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RsaController : ControllerBase
    {
        private readonly RsaService _rsaService;
        private readonly ILogger<RsaService> _logger;

        public RsaController(RsaService rsaService, ILogger<RsaService> logger)
        {
            this._rsaService = rsaService;
            this._logger = logger;
        }

        [HttpPost("generate")]
        public ActionResult<RsaGenerateKeyRequestDto> GenerateKey()
        {
            try {
                var result = _rsaService.generateKey();            
                return Ok( new { 
                        publicKey = result.getPublicKey(),
                        privateKey = result.getPrivateKey()
                     });
            } catch (Exception ex)
            {
                return StatusCode(500, $"Internal server error: {ex.Message}");
            }
            
        }

        [HttpPost("decryptMessage")]
        public ActionResult<EmrUserDto> getDecryptMessage([FromBody] DecryptMessageRequestDto request)
        {
            try {
                _logger.LogInformation(request.ciphertextBase64);
                return Ok(_rsaService.Decrypt(request.ciphertextBase64));
            } catch (Exception ex)
            {
                return StatusCode(500, $"Internal server error: {ex.Message}");
            }
            
        }
    }
}