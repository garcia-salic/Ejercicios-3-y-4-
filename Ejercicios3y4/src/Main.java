public class Main{
    public static void main(String[] args) {
        RentaMovil modelo = new RentaMovil();
        VistaConsola vista = new VistaConsola();
        Controlador controlador = new Controlador(modelo, vista);

        controlador.iniciar();
    }
}