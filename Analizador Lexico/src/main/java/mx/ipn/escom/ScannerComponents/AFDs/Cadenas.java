package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.CharArrayManager;
import mx.ipn.escom.ScannerComponents.TipoToken;

public class Cadenas implements Automata{
    private final CharArrayManager manager;
    private Object literal = null;

    public Cadenas(CharArrayManager manager) {
        this.manager = manager;
    }

    @Override
    public String getLexema() {
        String lexema = "";
        boolean continuar = false;
        do {
            char next = manager.getNextChar();
            if(next=='"'){
                continuar = !continuar;
            }
            lexema = lexema.concat(String.valueOf(next));
        } while (manager.hasNext()&&continuar);
        literal = lexema.substring(1,lexema.length()-1);
        return lexema;
    }

    @Override
    public TipoToken getTipoToken() {
        return TipoToken.CADENA;
    }

    @Override
    public Object getLiteral() {
        return literal;
    }
}
