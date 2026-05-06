namespace WebApplication1.Models
{
    public class BibliotecaUsuario
    {
        public int Id { get; set; }
        public int UsuarioId { get; set; }
        public Usuario Usuario { get; set; } = null!;

        public int VideojuegoId { get; set; }
        public Videojuego Videojuego { get; set; } = null!;

        public DateTime FechaAdquisicion { get; set; } = DateTime.Now;
    }
}
