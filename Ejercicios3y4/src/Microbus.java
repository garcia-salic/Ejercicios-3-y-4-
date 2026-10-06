public class Microbus extends Vehiculo{
    private int cantidadPasajeros;
    private boolean incluyePiloto;

    public Microbus(String placa, String marca, String modelo, double tarifaDiaria,
                    int cantidadPasajeros, boolean incluyePiloto) {
        super(placa, marca, modelo, tarifaDiaria);

        if (cantidadPasajeros <= 0) {
            throw new IllegalArgumentException("La cantidad de pasajeros debe ser mayor que cero.");
        }

        this.cantidadPasajeros = cantidadPasajeros;
        this.incluyePiloto = incluyePiloto;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public boolean isIncluyePiloto() {
        return incluyePiloto;
    }

    @Override
    public double calcularRecargo(int dias) {
        return incluyePiloto ? 250.0 * dias : 0.0;
    }

    @Override
    public boolean tieneLicenciaAdecuada(Cliente cliente) {
        if (incluyePiloto) {
            return true;
        }
        return cliente.tieneLicencia('B');
    }

    @Override
    public int getUmbralMantenimiento() {
        return 25;
    }

    @Override
    public String getCategoria() {
        return "Microbus";
    }

    @Override
    public String getCaracteristicas() {
        String piloto = incluyePiloto ? "Incluye piloto" : "Sin piloto";
        return "Pasajeros: " + cantidadPasajeros + ", " + piloto;
    }
}
