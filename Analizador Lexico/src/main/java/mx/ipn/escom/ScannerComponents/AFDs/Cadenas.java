package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.CharArrayManager;
import mx.ipn.escom.ScannerComponents.TipoToken;

import java.util.Optional;

public class Cadenas implements Automata {
    private final CharArrayManager manager;
    private Object literal = null;

    public Cadenas(CharArrayManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<String> getLexema() {
        String lexema = "";
        boolean continuar = false;
        do {
            char next = manager.getNextChar();
            if (next == '"') {
                continuar = !continuar;
            }
            if (next == '\n') {
                manager.backPosition();
                break;
            }
            lexema = lexema.concat(String.valueOf(next));
        } while (manager.hasNext() && continuar);
        if (!(lexema.charAt(0) == '"' && lexema.charAt(lexema.length() - 1) == '"')) {
            return Optional.empty();
        }
        literal = lexema.substring(1, lexema.length() - 1);
        return Optional.of(lexema);
    }

    @Override
    public TipoToken getTipoToken() {
        return TipoToken.CADENA;
    }

    @Override
    public Object getLiteral() {
        return (String)literal;
    }
}
