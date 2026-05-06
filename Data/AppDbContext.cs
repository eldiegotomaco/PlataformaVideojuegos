using Microsoft.EntityFrameworkCore;
using WebApplication1.Models;

namespace WebApplication1.Data
{
    // Agregamos ": DbContext" para que herede las funciones de Entity Framework
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<Rol> Roles { get; set; }
        public DbSet<Videojuego> Videojuegos { get; set; }
        public DbSet<Categoria> Categorias { get; set; }
        public DbSet<Plataforma> Plataformas { get; set; }
        public DbSet<Compra> Compras { get; set; }
        public DbSet<DetalleCompra> DetalleCompras { get; set; }
        public DbSet<BibliotecaUsuario> BibliotecaUsuarios { get; set; }
        public DbSet<Reseña> Reseñas { get; set; }
    }
}