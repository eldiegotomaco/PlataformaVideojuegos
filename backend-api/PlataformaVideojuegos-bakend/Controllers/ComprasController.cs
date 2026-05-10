using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using WebApplication1.Data;
using WebApplication1.Models;
namespace WebApplication1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ComprasController : ControllerBase
    {
        private readonly AppDbContext _context;

        public ComprasController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/Compras
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Compra>>> GetCompras()
        {
            return await _context.Compras.ToListAsync();
        }

        // GET: api/Compras/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Compra>> GetCompra(int id)
        {
            var compra = await _context.Compras.FindAsync(id);

            if (compra == null)
            {
                return NotFound();
            }

            return compra;
        }

        // PUT: api/Compras/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutCompra(int id, Compra compra)
        {
            if (id != compra.Id)
            {
                return BadRequest();
            }

            _context.Entry(compra).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CompraExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Compras
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Compra>> PostCompra(Compra compra)
        {
            _context.Compras.Add(compra);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCompra", new { id = compra.Id }, compra);
        }

        // DELETE: api/Compras/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCompra(int id)
        {
            var compra = await _context.Compras.FindAsync(id);
            if (compra == null)
            {
                return NotFound();
            }

            _context.Compras.Remove(compra);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool CompraExists(int id)
        {
            return _context.Compras.Any(e => e.Id == id);
        }


        [HttpPost("comprar/{usuarioId}/{videojuegoId}")]
        public async Task<IActionResult> ComprarJuego(int usuarioId, int videojuegoId)
        {
            var usuario = await _context.Usuarios.FindAsync(usuarioId);
            if (usuario == null)
                return BadRequest(new { mensaje = "Usuario no encontrado." });

            var videojuego = await _context.Videojuegos.FindAsync(videojuegoId);
            if (videojuego == null)
                return BadRequest(new { mensaje = "Videojuego no encontrado." });

            var yaExiste = await _context.BibliotecaUsuarios
                .AnyAsync(b => b.UsuarioId == usuarioId && b.VideojuegoId == videojuegoId);

            if (yaExiste)
                return BadRequest(new { mensaje = "Este juego ya está en tu biblioteca." });

            var compra = new Compra
            {
                UsuarioId = usuarioId,
                FechaCompra = DateTime.Now,
                Total = videojuego.Precio
            };

            _context.Compras.Add(compra);
            await _context.SaveChangesAsync();

            var detalle = new DetalleCompra
            {
                CompraId = compra.Id,
                VideojuegoId = videojuegoId,
                PrecioUnitario = videojuego.Precio
            };

            _context.DetalleCompras.Add(detalle);

            var biblioteca = new BibliotecaUsuario
            {
                UsuarioId = usuarioId,
                VideojuegoId = videojuegoId,
                FechaAdquisicion = DateTime.Now
            };

            _context.BibliotecaUsuarios.Add(biblioteca);

            await _context.SaveChangesAsync();

            return Ok(new { mensaje = "Juego comprado correctamente." });
        }
    }
}
