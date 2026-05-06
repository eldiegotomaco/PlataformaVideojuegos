public class Videojuego
{
    public int Id { get; set; }
    public string? Titulo { get; set; }
    public string? Descripcion { get; set; }
    public decimal Precio { get; set; }

    // Nueva propiedad para la imagen
    public string? ImagenUrl { get; set; }

    public int CategoriaId { get; set; }
    public Categoria? Categoria { get; set; }
    public List<Plataforma> Plataformas { get; set; } = new List<Plataforma>();
}