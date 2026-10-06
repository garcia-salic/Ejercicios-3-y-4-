public abstract class Vehiculo {
    public enum Estado {
        DISPONIBLE,
        ALQUILADO,
        MANTENIMIENTO
    }

    private String placa;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private Estado estado;
    private int diasDesdeMantenimiento;

    public Vehiculo(String placa, String marca, String modelo, double tarifaDiaria) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacia.");
        }
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacia.");
        }
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacio.");
        }
        if (tarifaDiaria <= 0) {
            throw new IllegalArgumentException("La tarifa diaria debe ser mayor que cero.");
        }

        this.placa = placa.trim();
        this.marca = marca.trim();
        this.modelo = modelo.trim();
        this.tarifaDiaria = tarifaDiaria;
        this.estado = Estado.DISPONIBLE;
        this.diasDesdeMantenimiento = 0;
    }

    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getTarifaDiaria() { return tarifaDiaria; }
    public Estado getEstado() { return estado; }
    public int getDiasDesdeMantenimiento() { return diasDesdeMantenimiento; }

    public boolean estaDisponible() {
        return estado == Estado.DISPONIBLE;
    }

    public void alquilar() {
        if (!estaDisponible()) {
            throw new IllegalStateException("El vehiculo no esta disponible.");
        }
        estado = Estado.ALQUILADO;
    }

    public void devolver() {
        if (estado != Estado.ALQUILADO) {
            throw new IllegalStateException("El vehiculo no esta alquilado.");
        }
        estado = Estado.DISPONIBLE;
    }

    public void finalizarMantenimiento() {
        if (estado != Estado.MANTENIMIENTO) {
            throw new IllegalStateException("El vehiculo no esta en mantenimiento.");
        }
        diasDesdeMantenimiento = 0;
        estado = Estado.DISPONIBLE;
    }

    public void registrarDiasAlquilado(int dias) {
        diasDesdeMantenimiento += dias;
    }

    public boolean debeEntrarEnMantenimiento() {
        return diasDesdeMantenimiento >= getUmbralMantenimiento();
    }

    public double calcularSubtotal(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los dias deben ser positivos.");
        }
        return (tarifaDiaria * dias) + calcularRecargo(dias);
    }

    public abstract double calcularRecargo(int dias);
    public abstract boolean tieneLicenciaAdecuada(Cliente cliente);
    public abstract int getUmbralMantenimiento();
    public abstract String getCategoria();
    public abstract String getCaracteristicas();

    public void enviarAMantenimiento() {
        if (estado != Estado.DISPONIBLE) {
            throw new IllegalStateException("El vehiculo debe estar disponible para entrar a mantenimiento.");
        }
        estado = Estado.MANTENIMIENTO;
    }   
}
