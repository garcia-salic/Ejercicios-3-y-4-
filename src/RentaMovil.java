import java.util.ArrayList;

public class RentaMovil {
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Cliente> clientes;
    private ArrayList<Alquiler> alquileres;
    private double ingresos;
    private int siguienteNumeroAlquiler;

    public RentaMovil() {
        vehiculos = new ArrayList<>();
        clientes = new ArrayList<>();
        alquileres = new ArrayList<>();
        ingresos = 0.0;
        siguienteNumeroAlquiler = 1;
    }

    public boolean registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null || buscarVehiculo(vehiculo.getPlaca()) != null) {
            return false;
        }

        vehiculos.add(vehiculo);
        return true;
    }

    public boolean registrarCliente(Cliente cliente) {
        if (cliente == null || buscarCliente(cliente.getIdentificador()) != null) {
            return false;
        }

        clientes.add(cliente);
        return true;
    }

    public Vehiculo buscarVehiculo(String placa) {
        if (placa == null) {
            return null;
        }

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa.trim())) {
                return vehiculo;
            }
        }

        return null;
    }

    public Cliente buscarCliente(String identificador) {
        if (identificador == null) {
            return null;
        }

        for (Cliente cliente : clientes) {
            if (cliente.getIdentificador().equalsIgnoreCase(identificador.trim())) {
                return cliente;
            }
        }

        return null;
    }

    public Cotizacion cotizar(String placa, String identificador, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        Cliente cliente = buscarCliente(identificador);

        if (vehiculo == null) {
            return new Cotizacion(false, "Vehiculo no encontrado.");
        }

        if (cliente == null) {
            return new Cotizacion(false, "Cliente no encontrado.");
        }

        if (dias <= 0) {
            return new Cotizacion(false, "Los dias deben ser positivos.");
        }

        double subtotal = vehiculo.calcularSubtotal(dias);
        double descuento = cliente.calcularDescuento(subtotal);
        double total = subtotal - descuento;

        boolean disponible = vehiculo.estaDisponible();
        boolean licencia = vehiculo.tieneLicenciaAdecuada(cliente);
        boolean limite = cliente.puedeAlquilar();

        String razones = "";

        if (!disponible) {
            razones += "Vehiculo no disponible. ";
        }

        if (!licencia) {
            razones += "Licencia inadecuada. ";
        }

        if (!limite) {
            razones += "Limite de alquileres activos alcanzado. ";
        }

        boolean puede = disponible && licencia && limite;

        return new Cotizacion(
                true,
                razones.trim(),
                vehiculo,
                cliente,
                dias,
                subtotal,
                descuento,
                total,
                puede
        );
    }

    public boolean confirmarAlquiler(String placa, String identificador, int dias) {
        Cotizacion cotizacion = cotizar(placa, identificador, dias);

        if (!cotizacion.esValida() || !cotizacion.puedeAlquilar()) {
            return false;
        }

        Vehiculo vehiculo = cotizacion.getVehiculo();
        Cliente cliente = cotizacion.getCliente();

        Alquiler alquiler = new Alquiler(
                siguienteNumeroAlquiler,
                cliente,
                vehiculo,
                dias,
                cotizacion.getSubtotal(),
                cotizacion.getDescuento(),
                cotizacion.getTotal()
        );

        alquileres.add(alquiler);
        cliente.agregarAlquiler(alquiler);
        vehiculo.alquilar();
        ingresos += alquiler.getTotal();

        siguienteNumeroAlquiler++;

        return true;
    }

    public boolean registrarDevolucion(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null || vehiculo.getEstado() != Vehiculo.Estado.ALQUILADO) {
            return false;
        }

        Alquiler alquilerActivo = buscarAlquilerActivo(vehiculo);

        if (alquilerActivo == null) {
            return false;
        }

        alquilerActivo.finalizar();
        vehiculo.registrarDiasAlquilado(alquilerActivo.getDias());

        vehiculo.devolver();

        if (vehiculo.debeEntrarEnMantenimiento()) {
            vehiculo.enviarAMantenimiento();
        }

        return true;
    }

    private Alquiler buscarAlquilerActivo(Vehiculo vehiculo) {
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getVehiculo() == vehiculo && alquiler.estaActivo()) {
                return alquiler;
            }
        }

        return null;
    }

    public boolean finalizarMantenimiento(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null || vehiculo.getEstado() != Vehiculo.Estado.MANTENIMIENTO) {
            return false;
        }

        vehiculo.finalizarMantenimiento();
        return true;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Alquiler> getAlquileres() {
        return alquileres;
    }

    public double getIngresos() {
        return ingresos;
    }

    public int contarVehiculosPorCategoria(String categoria) {
        int cantidad = 0;

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getCategoria().equals(categoria)) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public double getIngresosPorCategoria(String categoria) {
        double total = 0.0;

        for (Alquiler alquiler : alquileres) {
            if (alquiler.getVehiculo().getCategoria().equals(categoria)) {
                total += alquiler.getTotal();
            }
        }

        return total;
    }

    public int contarVehiculosPorEstado(Vehiculo.Estado estado) {
        int cantidad = 0;

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getEstado() == estado) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public ArrayList<Alquiler> getAlquileresActivos() {
        ArrayList<Alquiler> activos = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {
            if (alquiler.estaActivo()) {
                activos.add(alquiler);
            }
        }

        return activos;
    }
}
