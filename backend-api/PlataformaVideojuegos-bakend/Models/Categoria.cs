using WebApplication1.Models;

public class Categoria
{
    public int Id { get; set; }
    public string Nombre { get; set; } = string.Empty;

    public List<Videojuego> Videojuegos { get; set; } = new();
}