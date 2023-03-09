package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.CharArrayManager;
import mx.ipn.escom.ScannerComponents.TipoToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Simbolos implements Automata{
    private final CharArrayManager manager;
    private char simbolo;
    private static final Map<String,TipoToken> simbolos;
    static {
        simbolos = new HashMap<>();
        simbolos.put("+",TipoToken.SUMA);
        simbolos.put("-",TipoToken.RESTA);
        simbolos.put("*",TipoToken.MULTIPLICACION);
        simbolos.put("{",TipoToken.LLAVE_IZQUIERDA);
        simbolos.put("}",TipoToken.LLAVE_DERECHA);
        simbolos.put("[",TipoToken.CORCHETE_IZQUIERDO);
        simbolos.put("]",TipoToken.CORCHETE_DERECHO);
        simbolos.put("(",TipoToken.PARENTESIS_IZQUIERDO);
        simbolos.put(")",TipoToken.PARENTESIS_DERECHO);
        simbolos.put(",",TipoToken.COMA);
        simbolos.put(".",TipoToken.PUNTO);
        simbolos.put(";",TipoToken.PUNTO_Y_COMA);
    }

    public Simbolos(CharArrayManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<String> getLexema() {
        simbolo = manager.getNextChar();
        return Optional.of(String.valueOf(simbolo));
    }

    @Override
    public TipoToken getTipoToken() {
        return simbolos.get(String.valueOf(simbolo));
    }

    @Override
    public Object getLiteral() {
        return null;
    }
}
