public class CamionetaCarga extends Vehiculo{
    private double capacidadToneladas;

    public CamionetaCarga(String placa, String marca, String modelo, double tarifaDiaria,
                          double capacidadToneladas) {
        super(placa, marca, modelo, tarifaDiaria);

        if (capacidadToneladas <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }

        this.capacidadToneladas = capacidadToneladas;
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }

    @Override
    public double calcularRecargo(int dias) {
        return 100.0 * capacidadToneladas * dias;
    }

    @Override
    public boolean tieneLicenciaAdecuada(Cliente cliente) {
        return cliente.tieneLicencia('B');
    }

    @Override
    public int getUmbralMantenimiento() {
        return 15;
    }

    @Override
    public String getCategoria() {
        return "CamionetaCarga";
    }

    @Override
    public String getCaracteristicas() {
        return String.format("Capacidad: %.2f toneladas", capacidadToneladas);
    }
}
