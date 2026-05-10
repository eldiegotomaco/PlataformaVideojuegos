using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Identity;
using WebApplication1.Data;
using WebApplication1.DTOs;
using WebApplication1.Models;

namespace WebApplication1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly AppDbContext _context;

        public AuthController(AppDbContext context)
        {
            _context = context;
        }

        // =========================
        // REGISTER
        // =========================
        [HttpPost("register")]
        public async Task<IActionResult> Register(RegisterDto dto)
        {
            // Verificar si ya existe email
            var existe = await _context.Usuarios
                .AnyAsync(u => u.Email == dto.Email);

            if (existe)
            {
                return BadRequest(new
                {
                    mensaje = "El correo ya está registrado."
                });
            }

            // Crear usuario
            var usuario = new Usuario
            {
                Nombre = dto.Nombre,
                Email = dto.Email,
                RolId = 1// Cliente por defecto
            };

            // Hash de contraseña
            var passwordHasher = new PasswordHasher<Usuario>();

            usuario.PasswordHash =
                passwordHasher.HashPassword(
                    usuario,
                    dto.Password
                );

            _context.Usuarios.Add(usuario);

            await _context.SaveChangesAsync();

            return Ok(new
            {
                mensaje = "Usuario registrado correctamente."
            });
        }

        // =========================
        // LOGIN
        // =========================
        [HttpPost("login")]
        public async Task<IActionResult> Login(LoginDto dto)
        {
            var usuario = await _context.Usuarios
                .Include(u => u.Rol)
                .FirstOrDefaultAsync(
                    u => u.Email == dto.Email
                );

            if (usuario == null)
            {
                return Unauthorized(new
                {
                    mensaje = "Correo incorrecto."
                });
            }

            var passwordHasher = new PasswordHasher<Usuario>();

            var resultado =
                passwordHasher.VerifyHashedPassword(
                    usuario,
                    usuario.PasswordHash,
                    dto.Password
                );

            if (resultado ==
                PasswordVerificationResult.Failed)
            {
                return Unauthorized(new
                {
                    mensaje = "Contraseña incorrecta."
                });
            }

            return Ok(new AuthResponseDto
            {
                UsuarioId = usuario.Id,
                Nombre = usuario.Nombre,
                Email = usuario.Email,
                Rol = usuario.Rol.Nombre,
                Token = "TOKEN_TEMPORAL"
            });
        }
    }
}