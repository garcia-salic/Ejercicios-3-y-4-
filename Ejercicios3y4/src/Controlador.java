public class Controlador {
    private RentaMovil modelo;
    private VistaConsola vista;

    public Controlador(RentaMovil modelo, VistaConsola vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar() {
        cargarDatosIniciales();

        boolean salir = false;

        while (!salir) {
            vista.mostrarMenu();
            int opcion = vista.leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    registrarVehiculo();
                    break;
                case 2:
                    registrarCliente();
                    break;
                case 3:
                    mostrarFlota();
                    break;
                case 4:
                    mostrarClientes();
                    break;
                case 5:
                    cotizar();
                    break;
                case 6:
                    confirmarAlquiler();
                    break;
                case 7:
                    devolverVehiculo();
                    break;
                case 8:
                    finalizarMantenimiento();
                    break;
                case 9:
                    reporteFlota();
                    break;
                case 10:
                    reporteIngresos();
                    break;
                case 11:
                    mostrarAlquileresActivos();
                    break;
                case 12:
                    historialCliente();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    vista.mostrarError("Opcion no valida.");
            }
        }

        vista.mostrar("Programa finalizado.");
        vista.cerrar();
    }

    private void registrarVehiculo() {
        vista.mostrarSeparador();
        vista.mostrar("REGISTRO DE VEHICULO");
        vista.mostrar("1. Automovil");
        vista.mostrar("2. Motocicleta");
        vista.mostrar("3. Camioneta de carga");
        vista.mostrar("4. Microbus");

        int tipo = vista.leerEntero("Tipo: ");

        if (tipo < 1 || tipo > 4) {
            vista.mostrarError("Tipo de vehiculo no valido.");
            return;
        }

        String placa = vista.leerTexto("Placa: ");

        if (modelo.buscarVehiculo(placa) != null) {
            vista.mostrarError("Ya existe un vehiculo con esa placa.");
            return;
        }

        String marca = vista.leerTexto("Marca: ");
        String modeloVehiculo = vista.leerTexto("Modelo: ");
        double tarifa = vista.leerDoublePositivo("Tarifa diaria: ");

        try {
            Vehiculo vehiculo;

            if (tipo == 1) {
                int pasajeros = vista.leerEnteroPositivo("Cantidad de pasajeros: ");
                boolean automatica = vista.leerBooleano("Transmision automatica? (si/no): ");

                vehiculo = new Automovil(
                        placa, marca, modeloVehiculo, tarifa,
                        pasajeros, automatica
                );
            } else if (tipo == 2) {
                int cilindraje = vista.leerEnteroPositivo("Cilindraje: ");

                vehiculo = new Motocicleta(
                        placa, marca, modeloVehiculo, tarifa,
                        cilindraje
                );
            } else if (tipo == 3) {
                double capacidad = vista.leerDoublePositivo("Capacidad en toneladas: ");

                vehiculo = new CamionetaCarga(
                        placa, marca, modeloVehiculo, tarifa,
                        capacidad
                );
            } else {
                int pasajeros = vista.leerEnteroPositivo("Cantidad de pasajeros: ");
                boolean piloto = vista.leerBooleano("Incluye piloto? (si/no): ");

                vehiculo = new Microbus(
                        placa, marca, modeloVehiculo, tarifa,
                        pasajeros, piloto
                );
            }

            if (modelo.registrarVehiculo(vehiculo)) {
                vista.mostrar("Vehiculo registrado correctamente.");
            } else {
                vista.mostrarError("No se pudo registrar el vehiculo.");
            }

        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    private void registrarCliente() {
        vista.mostrarSeparador();
        vista.mostrar("REGISTRO DE CLIENTE");
        vista.mostrar("1. Cliente individual");
        vista.mostrar("2. Cliente corporativo");

        int tipo = vista.leerEntero("Tipo: ");

        if (tipo != 1 && tipo != 2) {
            vista.mostrarError("Tipo de cliente no valido.");
            return;
        }

        String identificador = vista.leerTexto(
                tipo == 1 ? "DPI: " : "NIT: "
        );

        if (modelo.buscarCliente(identificador) != null) {
            vista.mostrarError("Ya existe un cliente con ese identificador.");
            return;
        }

        String nombre = vista.leerTexto("Nombre: ");

        try {
            Cliente cliente;

            if (tipo == 1) {
                cliente = new ClienteIndividual(identificador, nombre);
            } else {
                String empresa = vista.leerTexto("Nombre de la empresa: ");
                String contacto = vista.leerTexto("Nombre del contacto: ");

                cliente = new ClienteCorporativo(
                        identificador, nombre, empresa, contacto
                );
            }

            int cantidadLicencias = vista.leerEnteroPositivo(
                    "Cantidad de licencias a registrar: "
            );

            for (int i = 0; i < cantidadLicencias; i++) {
                char licencia = vista.leerLicencia(
                        "Tipo de licencia #" + (i + 1) + " (A/B/C/M): "
                );
                cliente.agregarLicencia(new Licencia(licencia));
            }

            if (modelo.registrarCliente(cliente)) {
                vista.mostrar("Cliente registrado correctamente.");
            } else {
                vista.mostrarError("No se pudo registrar el cliente.");
            }

        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    private void mostrarFlota() {
        vista.mostrarSeparador();
        vista.mostrar("FLOTA");

        if (modelo.getVehiculos().isEmpty()) {
            vista.mostrar("No hay vehiculos registrados.");
            return;
        }

        for (Vehiculo vehiculo : modelo.getVehiculos()) {
            vista.mostrar(
                    "Placa: " + vehiculo.getPlaca()
                    + " | " + vehiculo.getMarca()
                    + " " + vehiculo.getModelo()
                    + " | Tarifa: Q" + String.format("%.2f", vehiculo.getTarifaDiaria())
                    + " | Estado: " + vehiculo.getEstado()
                    + " | " + vehiculo.getCaracteristicas()
            );
        }
    }

    private void mostrarClientes() {
        vista.mostrarSeparador();
        vista.mostrar("CLIENTES");

        if (modelo.getClientes().isEmpty()) {
            vista.mostrar("No hay clientes registrados.");
            return;
        }

        for (Cliente cliente : modelo.getClientes()) {
            vista.mostrar(cliente.getDescripcion());
        }
    }

    private void cotizar() {
        vista.mostrarSeparador();
        vista.mostrar("COTIZACION");

        String placa = vista.leerTexto("Placa: ");
        String identificador = vista.leerTexto("Identificador del cliente: ");
        int dias = vista.leerEnteroPositivo("Dias de alquiler: ");

        Cotizacion cotizacion = modelo.cotizar(placa, identificador, dias);

        if (!cotizacion.esValida()) {
            vista.mostrarError(cotizacion.getRazones());
            return;
        }

        mostrarCotizacion(cotizacion);
    }

    private void confirmarAlquiler() {
        vista.mostrarSeparador();
        vista.mostrar("CONFIRMAR ALQUILER");

        String placa = vista.leerTexto("Placa: ");
        String identificador = vista.leerTexto("Identificador del cliente: ");
        int dias = vista.leerEnteroPositivo("Dias de alquiler: ");

        Cotizacion cotizacion = modelo.cotizar(placa, identificador, dias);

        if (!cotizacion.esValida()) {
            vista.mostrarError(cotizacion.getRazones());
            return;
        }

        mostrarCotizacion(cotizacion);

        if (!cotizacion.puedeAlquilar()) {
            vista.mostrarError("El alquiler no puede confirmarse.");
            vista.mostrar("Razones: " + cotizacion.getRazones());
            return;
        }

        boolean aceptar = vista.leerBooleano("Desea confirmar? (si/no): ");

        if (!aceptar) {
            vista.mostrar("Operacion cancelada. No se modifico el sistema.");
            return;
        }

        if (modelo.confirmarAlquiler(placa, identificador, dias)) {
            vista.mostrar("Alquiler confirmado correctamente.");
        } else {
            vista.mostrarError("No se pudo confirmar el alquiler.");
        }
    }

    private void mostrarCotizacion(Cotizacion cotizacion) {
        vista.mostrar("Vehiculo: "
                + cotizacion.getVehiculo().getPlaca()
                + " | "
                + cotizacion.getVehiculo().getCaracteristicas());

        vista.mostrar("Estado: "
                + cotizacion.getVehiculo().getEstado());

        vista.mostrar(String.format("Subtotal: Q%.2f", cotizacion.getSubtotal()));
        vista.mostrar(String.format("Descuento: Q%.2f", cotizacion.getDescuento()));
        vista.mostrar(String.format("Total: Q%.2f", cotizacion.getTotal()));

        if (cotizacion.puedeAlquilar()) {
            vista.mostrar("El cliente puede alquilar el vehiculo.");
        } else {
            vista.mostrar("El cliente NO puede alquilar el vehiculo.");
            vista.mostrar("Razones: " + cotizacion.getRazones());
        }
    }

    private void devolverVehiculo() {
        vista.mostrarSeparador();
        vista.mostrar("DEVOLUCION");

        String placa = vista.leerTexto("Placa: ");

        if (modelo.registrarDevolucion(placa)) {
            Vehiculo vehiculo = modelo.buscarVehiculo(placa);

            if (vehiculo.getEstado() == Vehiculo.Estado.MANTENIMIENTO) {
                vista.mostrar("Devolucion registrada.");
                vista.mostrar("El vehiculo pasa a mantenimiento.");
            } else {
                vista.mostrar("Devolucion registrada. El vehiculo esta disponible.");
            }
        } else {
            vista.mostrarError(
                    "No se pudo registrar la devolucion. "
                    + "Verifique que la placa exista y el vehiculo este alquilado."
            );
        }
    }

    private void finalizarMantenimiento() {
        vista.mostrarSeparador();
        vista.mostrar("FIN DE MANTENIMIENTO");

        String placa = vista.leerTexto("Placa: ");

        if (modelo.finalizarMantenimiento(placa)) {
            vista.mostrar("Mantenimiento finalizado. El vehiculo esta disponible.");
        } else {
            vista.mostrarError(
                    "No se pudo finalizar el mantenimiento. "
                    + "Verifique que el vehiculo exista y este en mantenimiento."
            );
        }
    }

    private void reporteFlota() {
        vista.mostrarSeparador();
        vista.mostrar("REPORTE DE FLOTA");

        String[] categorias = {
            "Automovil",
            "Motocicleta",
            "Camioneta de carga",
            "Microbus"
        };

        for (String categoria : categorias) {
            vista.mostrar(categoria + ": "
                    + modelo.contarVehiculosPorCategoria(categoria));
        }

        vista.mostrar("");
        vista.mostrar("Disponibles: "
                + modelo.contarVehiculosPorEstado(Vehiculo.Estado.DISPONIBLE));
        vista.mostrar("Alquilados: "
                + modelo.contarVehiculosPorEstado(Vehiculo.Estado.ALQUILADO));
        vista.mostrar("En mantenimiento: "
                + modelo.contarVehiculosPorEstado(Vehiculo.Estado.MANTENIMIENTO));
    }

    private void reporteIngresos() {
        vista.mostrarSeparador();
        vista.mostrar("REPORTE DE INGRESOS");

        vista.mostrar(String.format(
                "Ingresos totales: Q%.2f",
                modelo.getIngresos()
        ));

        vista.mostrar("Por categoria:");

        String[] categorias = {
            "Automovil",
            "Motocicleta",
            "Camioneta de carga",
            "Microbus"
        };

        for (String categoria : categorias) {
            vista.mostrar(String.format(
                    "%s: Q%.2f",
                    categoria,
                    modelo.getIngresosPorCategoria(categoria)
            ));
        }

        double descuentos = 0.0;

        for (Alquiler alquiler : modelo.getAlquileres()) {
            descuentos += alquiler.getDescuento();
        }

        vista.mostrar(String.format(
                "Descuentos otorgados: Q%.2f",
                descuentos
        ));
    }

    private void mostrarAlquileresActivos() {
        vista.mostrarSeparador();
        vista.mostrar("ALQUILERES ACTIVOS");

        if (modelo.getAlquileresActivos().isEmpty()) {
            vista.mostrar("No hay alquileres activos.");
            return;
        }

        for (Alquiler alquiler : modelo.getAlquileresActivos()) {
            vista.mostrar(
                    "#" + alquiler.getNumero()
                    + " | Cliente: " + alquiler.getCliente().getIdentificador()
                    + " | Vehiculo: " + alquiler.getVehiculo().getPlaca()
                    + " | Dias: " + alquiler.getDias()
                    + String.format(" | Total: Q%.2f", alquiler.getTotal())
            );
        }
    }

    private void historialCliente() {
        vista.mostrarSeparador();

        String identificador = vista.leerTexto(
                "Identificador del cliente: "
        );

        Cliente cliente = modelo.buscarCliente(identificador);

        if (cliente == null) {
            vista.mostrarError("Cliente no encontrado.");
            return;
        }

        vista.mostrar(cliente.getDescripcion());
        vista.mostrar("Historial:");

        if (cliente.getAlquileres().isEmpty()) {
            vista.mostrar("No tiene alquileres.");
        } else {
            for (Alquiler alquiler : cliente.getAlquileres()) {
                vista.mostrar(
                        "#" + alquiler.getNumero()
                        + " | Vehiculo: " + alquiler.getVehiculo().getPlaca()
                        + " | Dias: " + alquiler.getDias()
                        + String.format(" | Total: Q%.2f", alquiler.getTotal())
                );
            }
        }

        vista.mostrar(String.format(
                "Total pagado: Q%.2f",
                cliente.getTotalPagado()
        ));
    }

    private void cargarDatosIniciales() {
        try {
            // Automoviles
            modelo.registrarVehiculo(
                    new Automovil("P001ABC", "Toyota", "Corolla", 300,
                            5, true)
            );

            modelo.registrarVehiculo(
                    new Automovil("P002ABC", "Honda", "Civic", 250,
                            5, false)
            );

            // Motocicletas
            modelo.registrarVehiculo(
                    new Motocicleta("M001ABC", "Honda", "CB250", 150,
                            250)
            );

            modelo.registrarVehiculo(
                    new Motocicleta("M002ABC", "Yamaha", "MT300", 180,
                            300)
            );

            // Camionetas
            modelo.registrarVehiculo(
                    new CamionetaCarga("C001ABC", "Toyota", "Hilux", 200,
                            1.5)
            );

            modelo.registrarVehiculo(
                    new CamionetaCarga("C002ABC", "Ford", "Ranger", 220,
                            2.0)
            );

            // Microbuses
            modelo.registrarVehiculo(
                    new Microbus("B001ABC", "Toyota", "Hiace", 450,
                            15, true)
            );

            modelo.registrarVehiculo(
                    new Microbus("B002ABC", "Hyundai", "H1", 400,
                            12, false)
            );

            // Clientes individuales
            ClienteIndividual individual1 =
                    new ClienteIndividual("1234567890123", "Ana Lopez");
            individual1.agregarLicencia(new Licencia('C'));
            modelo.registrarCliente(individual1);

            ClienteIndividual individual2 =
                    new ClienteIndividual("9876543210987", "Carlos Perez");
            individual2.agregarLicencia(new Licencia('M'));
            individual2.agregarLicencia(new Licencia('C'));
            modelo.registrarCliente(individual2);

            // Clientes corporativos
            ClienteCorporativo corporativo1 =
                    new ClienteCorporativo(
                            "1234567-8",
                            "Empresa Uno",
                            "Transportes Uno",
                            "Maria Garcia"
                    );
            corporativo1.agregarLicencia(new Licencia('B'));
            modelo.registrarCliente(corporativo1);

            ClienteCorporativo corporativo2 =
                    new ClienteCorporativo(
                            "7654321-9",
                            "Empresa Dos",
                            "Servicios Dos",
                            "Pedro Gomez"
                    );
            corporativo2.agregarLicencia(new Licencia('A'));
            modelo.registrarCliente(corporativo2);

            vista.mostrar("Datos iniciales cargados correctamente.");

        } catch (IllegalArgumentException e) {
            vista.mostrarError(
                    "No se pudieron cargar los datos iniciales: "
                    + e.getMessage()
            );
        }
    }
}
