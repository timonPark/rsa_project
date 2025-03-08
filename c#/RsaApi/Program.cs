using RsaApi.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddOpenApi().AddSingleton<RsaService>();
builder.Services.AddControllers();

var app = builder.Build();

app.MapControllers();

app.Run();
