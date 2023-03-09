package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.CharArrayManager;
import mx.ipn.escom.ScannerComponents.TipoToken;

import java.util.Optional;

public class Comentarios implements Automata {
    private final CharArrayManager manager;
    private int aumento;
    private boolean setAumento = false;
    private TipoToken tipoToken;

    public Comentarios(CharArrayManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<String> getLexema() {
        String lexema = "";
        Optional<String> wraper = Optional.empty();
        int status = 0;
        boolean continuar = true;
        while (manager.hasNext() && continuar) {
            char next = manager.getNextChar();
            switch (status) {
                case 0:
                    if (next == '/') {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 1;
                    }
                    break;
                case 1:
                    if (next == '/') {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 2;
                    } else if (next == '*') {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 3;
                    } else {
                        status = 5;
                    }
                    break;
                case 2:
                    tipoToken = TipoToken.COMENTARIOS;
                    if (next != '\n') {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 2;
                    } else {
                        status = 6;
                    }
                    break;
                case 3:
                    tipoToken = TipoToken.COMENTARIOS;
                    if (next == '*') {
                        lexema = lexema.concat(String.valueOf(next));
                        status = 4;
                    } else if(next=='\n'){
                        aumento++;
                    }else{
                        lexema = lexema.concat(String.valueOf(next));
                    }
                    break;
                case 4:
                    if(next=='/'){
                        lexema = lexema.concat(String.valueOf(next));
                        status=7;
                    } else if (next=='*') {
                        lexema = lexema.concat(String.valueOf(next));
                        status=4;
                    } else{
                        lexema = lexema.concat(String.valueOf(next));
                        status = 3;
                    }
                    break;

            }
            if (status == 5) {
                continuar = false;
                manager.backPosition();
                tipoToken = TipoToken.DIVISION;
                wraper = Optional.of(lexema);
            }
            if (status == 6) {
                continuar = false;
                manager.backPosition();
                tipoToken = TipoToken.COMENTARIOS;
                wraper = Optional.of(lexema);
            }
            if(status == 7){
                setAumento = true;
                continuar=false;
                tipoToken = TipoToken.COMENTARIOS;
                wraper = Optional.of(lexema);
            }
            if(!manager.hasNext()&&status==2){
                continuar = false;
                tipoToken = TipoToken.COMENTARIOS;
                wraper = Optional.of(lexema);
            }
            if(!manager.hasNext()&&status==3){
                continuar = false;
                tipoToken = TipoToken.COMENTARIOS;
                wraper = Optional.empty();
            }
        }
        return wraper;
    }

    @Override
    public TipoToken getTipoToken() {
        return tipoToken;
    }
    @Override
    public Object getLiteral() {
        return null;
    }
    public int getAumento() {
        int aux = aumento;
        boolean auxaumento = setAumento;
        setAumento = false;
        aumento = 0;
        return auxaumento?aux:0;
    }
}
