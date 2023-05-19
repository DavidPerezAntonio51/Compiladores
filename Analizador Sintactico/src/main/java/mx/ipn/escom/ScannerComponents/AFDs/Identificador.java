package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.CharArrayManager;
import mx.ipn.escom.ScannerComponents.TipoToken;

import java.util.Optional;


public class Identificador implements Automata{
    private final CharArrayManager manager;
    public Identificador(CharArrayManager manager){
        this.manager=manager;
    }
    @Override
    public Optional<String> getLexema(){
        String lexema = "";
        boolean continuar = true;
        while (manager.hasNext()&&continuar) {
            char next = manager.getNextChar();
            if(AFDManager.LETRAS.contains(String.valueOf(next))||AFDManager.NUMEROS.contains(String.valueOf(next))){
                lexema = lexema.concat(String.valueOf(next));
            }else{
                continuar = false;
                manager.backPosition();
            }
        }
        return Optional.of(lexema);
    }

    @Override
    public TipoToken getTipoToken() {
        return TipoToken.IDENTIFICADOR;
    }

    @Override
    public Object getLiteral() {
        return null;
    }
}
