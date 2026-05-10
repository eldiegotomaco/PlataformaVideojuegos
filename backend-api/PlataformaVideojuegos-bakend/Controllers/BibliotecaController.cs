using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using WebApplication1.Data;
using WebApplication1.Models;

namespace WebApplication1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BibliotecaController : ControllerBase
    {
        private readonly AppDbContext _context;

        public BibliotecaController(AppDbContext context)
        {
            _context = context;
        }

        [HttpGet("usuario/{usuarioId}")]
        public async Task<ActionResult<IEnumerable<BibliotecaUsuario>>> GetBibliotecaUsuario(int usuarioId)
        {
            var biblioteca = await _context.BibliotecaUsuarios
                .Include(b => b.Videojuego)
                .Where(b => b.UsuarioId == usuarioId)
                .ToListAsync();

            return Ok(biblioteca);
        }
    }
}