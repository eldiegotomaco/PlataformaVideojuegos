namespace WebApplication1.Models
{
    public class Compra
    {
        public int Id { get; set; }
        public DateTime FechaCompra { get; set; } = DateTime.Now;
        public decimal Total { get; set; }

        public int UsuarioId { get; set; }
        public Usuario Usuario { get; set; } = null!;

        public List<DetalleCompra> Detalles { get; set; } = new();
    }
}
