public class Automovil extends Vehiculo{
    private int cantidadPasajeros;
    private boolean transmisionAutomatica;

    public Automovil(String placa, String marca, String modelo, double tarifaDiaria,
                     int cantidadPasajeros, boolean transmisionAutomatica) {
        super(placa, marca, modelo, tarifaDiaria);

        if (cantidadPasajeros <= 0) {
            throw new IllegalArgumentException("La cantidad de pasajeros debe ser mayor que cero.");
        }

        this.cantidadPasajeros = cantidadPasajeros;
        this.transmisionAutomatica = transmisionAutomatica;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public boolean isTransmisionAutomatica() {
        return transmisionAutomatica;
    }

    @Override
    public double calcularRecargo(int dias) {
        if (transmisionAutomatica) {
            return 50.0 * dias;
        }
        return 0.0;
    }

    @Override
    public boolean tieneLicenciaAdecuada(Cliente cliente) {
        return cliente.tieneLicencia('C');
    }

    @Override
    public int getUmbralMantenimiento() {
        return 30;
    }

    @Override
    public String getCategoria() {
        return "Automovil";
    }

    @Override
    public String getCaracteristicas() {
        String transmision = transmisionAutomatica ? "Automatica" : "Manual";
        return "Pasajeros: " + cantidadPasajeros + ", Transmision: " + transmision;
    }
}
