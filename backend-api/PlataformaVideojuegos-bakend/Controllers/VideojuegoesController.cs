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
    public class VideojuegoesController : ControllerBase
    {
        private readonly AppDbContext _context;

        public VideojuegoesController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/Videojuegoes
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Videojuego>>> GetVideojuegos()
        {
            return await _context.Videojuegos.ToListAsync();
        }

        // GET: api/Videojuegoes/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Videojuego>> GetVideojuego(int id)
        {
            var videojuego = await _context.Videojuegos.FindAsync(id);

            if (videojuego == null)
            {
                return NotFound();
            }

            return videojuego;
        }

        // PUT: api/Videojuegoes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutVideojuego(int id, Videojuego videojuego)
        {
            if (id != videojuego.Id)
            {
                return BadRequest();
            }

            _context.Entry(videojuego).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!VideojuegoExists(id))
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

        // POST: api/Videojuegoes
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Videojuego>> PostVideojuego(Videojuego videojuego)
        {
            _context.Videojuegos.Add(videojuego);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetVideojuego", new { id = videojuego.Id }, videojuego);
        }

        // DELETE: api/Videojuegoes/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteVideojuego(int id)
        {
            var videojuego = await _context.Videojuegos.FindAsync(id);
            if (videojuego == null)
            {
                return NotFound();
            }

            _context.Videojuegos.Remove(videojuego);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool VideojuegoExists(int id)
        {
            return _context.Videojuegos.Any(e => e.Id == id);
        }
    }
}
