namespace WebApplication1.Models
{
    public class Reseña
    {
        public int Id { get; set; }
        public string Comentario { get; set; } = string.Empty;
        public int Calificacion { get; set; }

        public int UsuarioId { get; set; }
        public Usuario Usuario { get; set; } = null!;

        public int VideojuegoId { get; set; }
        public Videojuego Videojuego { get; set; } = null!;
    }
}
