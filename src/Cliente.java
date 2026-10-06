import java.util.ArrayList;
public abstract class Cliente {
    private String identificador;
    private String nombre;
    private ArrayList<Licencia> licencias;
    private ArrayList<Alquiler> alquileres;

    public Cliente(String identificador, String nombre) {
        if (identificador == null || identificador.trim().isEmpty()) {
            throw new IllegalArgumentException("El identificador no puede estar vacio.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        this.identificador = identificador.trim();
        this.nombre = nombre.trim();
        this.licencias = new ArrayList<>();
        this.alquileres = new ArrayList<>();
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarLicencia(Licencia licencia) {
        if (licencia == null) {
            throw new IllegalArgumentException("La licencia no puede ser nula.");
        }

        if (!tieneLicencia(licencia.getTipo())) {
            licencias.add(licencia);
        }
    }

    public boolean tieneLicencia(char tipo) {
        tipo = Character.toUpperCase(tipo);

        for (Licencia licencia : licencias) {
            if (licencia.autoriza(tipo)) {
                return true;
            }
        }

        return false;
    }

    public int getAlquileresActivos() {
        int cantidad = 0;

        for (Alquiler alquiler : alquileres) {
            if (alquiler.estaActivo()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public void agregarAlquiler(Alquiler alquiler) {
        if (alquiler == null) {
            throw new IllegalArgumentException("El alquiler no puede ser nulo.");
        }

        alquileres.add(alquiler);
    }

    public double getTotalPagado() {
        double total = 0.0;

        for (Alquiler alquiler : alquileres) {
            total += alquiler.getTotal();
        }

        return total;
    }

    public ArrayList<Alquiler> getAlquileres() {
        return alquileres;
    }

    public abstract boolean puedeAlquilar();

    public abstract double calcularDescuento(double subtotal);

    public abstract String getDescripcion();
} 
