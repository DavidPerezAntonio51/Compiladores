package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.CharArrayManager;
import mx.ipn.escom.ScannerComponents.TipoToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Operadores implements Automata{
    private final CharArrayManager manager;
    private String lexema;
    private static final Map<String,TipoToken> operadores;
    static {
        operadores = new HashMap<>();
        operadores.put("<",TipoToken.MENOR_QUE);
        operadores.put(">",TipoToken.MAYOR_QUE);
        operadores.put("=",TipoToken.ASIGNAR);
        operadores.put("!",TipoToken.NEGACION);
        operadores.put("<=",TipoToken.MENOR_O_IGUAL);
        operadores.put(">=",TipoToken.MAYOR_O_IGUAL);
        operadores.put("!=",TipoToken.DIFERENTE);
        operadores.put("==",TipoToken.IGUAL_A);
    }

    public Operadores(CharArrayManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<String> getLexema() {
        String lexema = "";
        Optional<String> wraper = Optional.empty();
        boolean continuar = true;
        int status = 0;
        while (manager.hasNext()&&continuar){
            char next = manager.getNextChar();
            switch (status){
                case 0:
                    if(AFDManager.OPERADORES.contains(String.valueOf(next))){
                        lexema = lexema.concat(String.valueOf(next));
                        status = 1;
                    }
                break;
                case 1:
                    if(next=='='){
                        lexema = lexema.concat(String.valueOf(next));
                        status = 2;
                    }else{
                        status = 3;
                    }
                    break;
            }
            if(status == 2){
                continuar=false;
                wraper = Optional.of(lexema);
                this.lexema = lexema;
            }
            if(status == 3){
                continuar = false;
                wraper = Optional.of(lexema);
                manager.backPosition();
                this.lexema = lexema;
            }
            if(status==1&& !manager.hasNext()){
                wraper = Optional.of(lexema);
                this.lexema = lexema;
            }
        }
        return wraper;
    }

    @Override
    public TipoToken getTipoToken() {
        return operadores.get(lexema);
    }

    @Override
    public Object getLiteral() {
        return null;
    }
}
