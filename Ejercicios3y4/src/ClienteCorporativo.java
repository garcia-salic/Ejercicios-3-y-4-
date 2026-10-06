public class ClienteCorporativo extends Cliente {

    private String nombreEmpresa;
    private String nombreContacto;

    public ClienteCorporativo(String nit, String nombre, String nombreEmpresa, String nombreContacto) {
        super(nit, nombre);

        if (nombreEmpresa == null || nombreEmpresa.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la empresa no puede estar vacio.");
        }
        if (nombreContacto == null || nombreContacto.trim().isEmpty()) {
            throw new IllegalArgumentException("El contacto no puede estar vacio.");
        }

        this.nombreEmpresa = nombreEmpresa.trim();
        this.nombreContacto = nombreContacto.trim();
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    @Override
    public boolean puedeAlquilar() {
        return getAlquileresActivos() < 3;
    }

    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * 0.10;
    }

    @Override
    public String getDescripcion() {
        return "Corporativo | NIT: " + getIdentificador()
                + " | Empresa: " + nombreEmpresa
                + " | Contacto: " + nombreContacto
                + " | Activos: " + getAlquileresActivos();
    }
}
