package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.CharArrayManager;
import mx.ipn.escom.ScannerComponents.TipoToken;

public class Numeros implements Automata {
    private final CharArrayManager manager;
    private Object literal = null;

    public Numeros(CharArrayManager manager) {
        this.manager = manager;
    }

    @Override
    public String getLexema() {
        String lexema = "";
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
                    }
                    break;
                case 3:
                    if (AFDManager.NUMEROS.contains(String.valueOf(next))) {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 3;
                    }else if(next=='E'){
                        lexema = lexema.concat(String.valueOf(next));
                        status = 4;
                    }else{
                        status = 7;
                    }
                    break;
                case 4:
                    if(next=='+'||next=='-'){
                        lexema = lexema.concat(String.valueOf(next));
                        status = 5;
                    }
                    if (AFDManager.NUMEROS.contains(String.valueOf(next))) {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 6;
                    }
                    break;
            }
            if (status == 7) {
                continuar = false;
                manager.backPosition();
            }
        }
        literal = lexema;
        return lexema;
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
