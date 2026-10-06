public class Licencia {
    private char tipo;

    public Licencia(char tipo) {
        tipo = Character.toUpperCase(tipo);

        if (tipo != 'A' && tipo != 'B' && tipo != 'C' && tipo != 'M') {
            throw new IllegalArgumentException("Tipo de licencia invalido.");
        }

        this.tipo = tipo;
    }

    public char getTipo() {
        return tipo;
    }

    public boolean autoriza(char requerida) {
        requerida = Character.toUpperCase(requerida);

        if (tipo == 'M') {
            return requerida == 'M';
        }

        if (requerida == 'M') {
            return false;
        }

        if (tipo == 'A') {
            return requerida == 'A' || requerida == 'B' || requerida == 'C';
        }

        if (tipo == 'B') {
            return requerida == 'B' || requerida == 'C';
        }

        return requerida == 'C';
    }
}
