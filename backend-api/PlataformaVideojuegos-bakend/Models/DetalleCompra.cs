namespace WebApplication1.Models
{
    public class DetalleCompra
    {
        public int Id { get; set; }
        public int CompraId { get; set; }
        public Compra Compra { get; set; } = null!;

        public int VideojuegoId { get; set; }
        public Videojuego Videojuego { get; set; } = null!;

        public decimal PrecioUnitario { get; set; }
    }
}
