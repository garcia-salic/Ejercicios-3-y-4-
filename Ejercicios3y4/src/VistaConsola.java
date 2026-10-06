import java.util.Scanner;

public class VistaConsola {
    private Scanner scanner;

    public VistaConsola() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println();
        System.out.println("========== RENTAMOVIL ==========");
        System.out.println("1. Registrar vehiculo");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Consultar flota");
        System.out.println("4. Consultar clientes");
        System.out.println("5. Cotizar alquiler");
        System.out.println("6. Confirmar alquiler");
        System.out.println("7. Devolver vehiculo");
        System.out.println("8. Finalizar mantenimiento");
        System.out.println("9. Reporte de flota");
        System.out.println("10. Reporte de ingresos");
        System.out.println("11. Alquileres activos");
        System.out.println("12. Historial de cliente");
        System.out.println("0. Salir");
        System.out.println("================================");
    }

    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                mostrarError("Ingrese un numero entero valido.");
            }
        }
    }

    public int leerEnteroPositivo(String mensaje) {
        while (true) {
            int valor = leerEntero(mensaje);

            if (valor > 0) {
                return valor;
            }

            mostrarError("El valor debe ser mayor que cero.");
        }
    }

    public double leerDoublePositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());

                if (valor > 0) {
                    return valor;
                }

                mostrarError("El valor debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                mostrarError("Ingrese un numero valido.");
            }
        }
    }

    public String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            mostrarError("El valor no puede estar vacio.");
        }
    }

    public char leerLicencia(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje).toUpperCase();

            if (texto.length() == 1 &&
                (texto.charAt(0) == 'A' ||
                 texto.charAt(0) == 'B' ||
                 texto.charAt(0) == 'C' ||
                 texto.charAt(0) == 'M')) {
                return texto.charAt(0);
            }

            mostrarError("La licencia debe ser A, B, C o M.");
        }
    }

    public boolean leerBooleano(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje).toLowerCase();

            if (texto.equals("s") || texto.equals("si")) {
                return true;
            }

            if (texto.equals("n") || texto.equals("no")) {
                return false;
            }

            mostrarError("Responda si/no.");
        }
    }

    public void mostrar(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }

    public void mostrarSeparador() {
        System.out.println("----------------------------------------");
    }

    public void cerrar() {
        scanner.close();
    }
}
