public class Motocicleta extends Vehiculo {
    private int cilindraje;

    public Motocicleta(String placa, String marca, String modelo, double tarifaDiaria,
                       int cilindraje) {
        super(placa, marca, modelo, tarifaDiaria);

        if (cilindraje <= 0) {
            throw new IllegalArgumentException("El cilindraje debe ser mayor que cero.");
        }

        this.cilindraje = cilindraje;
    }

    public int getCilindraje() {
        return cilindraje;
    }

    @Override
    public double calcularRecargo(int dias) {
        return cilindraje > 250 ? 75.0 : 0.0;
    }

    @Override
    public boolean tieneLicenciaAdecuada(Cliente cliente) {
        return cliente.tieneLicencia('M');
    }

    @Override
    public int getUmbralMantenimiento() {
        return 20;
    }

    @Override
    public String getCategoria() {
        return "Motocicleta";
    }

    @Override
    public String getCaracteristicas() {
        return "Cilindraje: " + cilindraje + " cc";
    }
}
