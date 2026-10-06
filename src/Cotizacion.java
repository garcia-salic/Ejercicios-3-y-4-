public class Cotizacion {
    private boolean valida;
    private String razones;
    private Vehiculo vehiculo;
    private Cliente cliente;
    private int dias;
    private double subtotal;
    private double descuento;
    private double total;
    private boolean puedeAlquilar;

    public Cotizacion(boolean valida, String razones) {
        this.valida = valida;
        this.razones = razones;
        this.puedeAlquilar = false;
    }

    public Cotizacion(boolean valida, String razones, Vehiculo vehiculo, Cliente cliente,
                      int dias, double subtotal, double descuento, double total,
                      boolean puedeAlquilar) {
        this.valida = valida;
        this.razones = razones;
        this.vehiculo = vehiculo;
        this.cliente = cliente;
        this.dias = dias;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
        this.puedeAlquilar = puedeAlquilar;
    }

    public boolean esValida() {
        return valida;
    }

    public String getRazones() {
        return razones;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getDias() {
        return dias;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }

    public boolean puedeAlquilar() {
        return puedeAlquilar;
    }
}
