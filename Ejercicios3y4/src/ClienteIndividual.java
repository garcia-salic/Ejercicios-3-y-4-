public class ClienteIndividual extends Cliente{
    private int alquileresConfirmados;

    public ClienteIndividual(String dpi, String nombre) {
        super(dpi, nombre);

        if (dpi == null || !dpi.matches("\\d{13}")) {
            throw new IllegalArgumentException("El DPI debe tener exactamente 13 digitos.");
        }

        alquileresConfirmados = 0;
    }

    @Override
    public boolean puedeAlquilar() {
        return getAlquileresActivos() < 1;
    }

    @Override
    public double calcularDescuento(double subtotal) {
        if (alquileresConfirmados >= 3) {
            return subtotal * 0.05;
        }

        return 0.0;
    }

    @Override
    public void agregarAlquiler(Alquiler alquiler) {
        super.agregarAlquiler(alquiler);
        alquileresConfirmados++;
    }

    @Override
    public String getDescripcion() {
        return "Individual | DPI: " + getIdentificador()
                + " | Nombre: " + getNombre()
                + " | Alquileres confirmados: " + alquileresConfirmados
                + " | Activos: " + getAlquileresActivos();
    }

    public int getAlquileresConfirmados() {
        return alquileresConfirmados;
    }
}
