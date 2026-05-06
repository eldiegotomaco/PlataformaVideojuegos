namespace WebApplication1.Models
{
    public class Videojuego
    {
        public int Id { get; set; }
        public string Titulo { get; set; } = string.Empty;
        public string Descripcion { get; set; } = string.Empty;
        public decimal Precio { get; set; }

        public int CategoriaId { get; set; }

        // CAMBIO AQUÍ: Agregamos el '?' y quitamos el 'null!'
        public Categoria? Categoria { get; set; }

        public List<Plataforma>? Plataformas { get; set; } = new();
    }
}