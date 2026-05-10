namespace WebApplication1.Models
{
    public class Rol
    {
        public int Id { get; set; }
        public string Nombre { get; set; } = string.Empty;

        // Relación: Un rol tiene muchos usuarios
        public List<Usuario> Usuarios { get; set; } = new();
    }
}
