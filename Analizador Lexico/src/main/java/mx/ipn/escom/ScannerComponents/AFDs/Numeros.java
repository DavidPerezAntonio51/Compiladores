package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.CharArrayManager;
import mx.ipn.escom.ScannerComponents.TipoToken;

import java.util.Optional;

public class Numeros implements Automata {
    private final CharArrayManager manager;
    private Object literal = null;

    public Numeros(CharArrayManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<String> getLexema() {
        String lexema = "";
        Optional wraper = Optional.empty();
        boolean continuar = true;
        int status = 0;
        while (manager.hasNext() && continuar) {
            char next = manager.getNextChar();
            switch (status) {
                case 0:
                    if (AFDManager.NUMEROS.contains(String.valueOf(next))) {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 1;
                    }
                    break;
                case 1:
                    if (AFDManager.NUMEROS.contains(String.valueOf(next))) {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 1;
                    } else if (next == '.') {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 2;
                    } else {
                        status = 7;
                    }
                    break;
                case 2:
                    if (AFDManager.NUMEROS.contains(String.valueOf(next))) {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 3;
                    } else {
                        status = 8;
                    }
                    break;
                case 3:
                    if (AFDManager.NUMEROS.contains(String.valueOf(next))) {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 3;
                    } else if (next == 'E') {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 4;
                    } else {
                        status = 7;
                    }
                    break;
                case 4:
                    if (next == '+' || next == '-') {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 5;
                    } else if (AFDManager.NUMEROS.contains(String.valueOf(next))) {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 6;
                    } else {
                        status = 8;
                    }
                    break;
                case 5:
                    if (AFDManager.NUMEROS.contains(String.valueOf(next))) {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 6;
                    } else {
                        status = 8;
                    }
                    break;
                case 6:
                    if (AFDManager.NUMEROS.contains(String.valueOf(next))) {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 6;
                    }else{
                        status = 7;
                    }
                    break;
            }
            /** Hemos llegado al estado de aceptacion asi que detenemos el ciclo, regresamos el apuntador una posicion
             * ya que es posible que necesitemos el caracter con el que rompimos el ciclo y encapsulamos el lexema
             * obtenido. */
            if (status == 7) {
                continuar = false;
                wraper = Optional.of(lexema);
                manager.backPosition();
            }
            /** Hemos llegado a un estado de error, asi terminamos el ciclo y regresamos un caracter atras en caso
             * de necesitarlo. */
            if (status == 8) {
                continuar = false;
                manager.backPosition();
            }
        }
        literal = lexema;
        return wraper;
    }

    @Override
    public TipoToken getTipoToken() {
        return TipoToken.NUMERO;
    }

    @Override
    public Object getLiteral() {
        return literal;
    }
}
