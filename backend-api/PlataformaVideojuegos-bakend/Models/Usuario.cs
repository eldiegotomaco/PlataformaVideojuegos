namespace WebApplication1.Models
{
    public class Usuario
    {
        public int Id { get; set; }
        public string Nombre { get; set; } = string.Empty; // Agrega esto
        public string Email { get; set; } = string.Empty;  // Agrega esto
        public string PasswordHash { get; set; } = string.Empty;

        public int RolId { get; set; }
        public Rol Rol { get; set; } = null!; // El ! le dice a C# que confíe en que EF lo llenará
    }
}
