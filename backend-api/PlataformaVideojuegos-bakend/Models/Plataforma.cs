using WebApplication1.Models;

public class Plataforma
{
    public int Id { get; set; }
    public string Nombre { get; set; } = string.Empty;

    public List<Videojuego> Videojuegos { get; set; } = new();
}