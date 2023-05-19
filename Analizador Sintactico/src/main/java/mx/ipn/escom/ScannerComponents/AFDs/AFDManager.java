package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.CharArrayManager;

import java.util.Optional;


public class AFDManager {
    protected static final String LETRAS = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    protected static final String NUMEROS = "0123456789";
    protected static final String SIMBOLOS_SIMPLES = "+-*()[]{};.,";
    protected static final String OPERADORES = "><=!";
    private final Identificador identificador;
    private final Cadenas cadenas;
    private final Simbolos simbolos;

    public AFDManager(CharArrayManager manager) {
        identificador = new Identificador(manager);
        cadenas = new Cadenas(manager);
        simbolos = new Simbolos(manager);
    }

    public Optional<Automata> getAFD(TipoAFD type) {
        return switch (type){
            case IDENTIFICADOR->Optional.of(this.identificador);
            case CADENA -> Optional.of(this.cadenas);
            case NUMEROS,COMENTARIOS_Y_SLASH,OPERADOR_RELACIONAL,NINGUNO -> Optional.empty();
            case SIMBOLO_SIMPLE -> Optional.of(this.simbolos);
        };
    }

    public TipoAFD checkType(char c){
        if(LETRAS.contains(String.valueOf(c))){
            return TipoAFD.IDENTIFICADOR;
        }
        if(c == '"'){
            return TipoAFD.CADENA;
        }
        if(NUMEROS.contains(String.valueOf(c))){
            return TipoAFD.NUMEROS;
        }
        if(SIMBOLOS_SIMPLES.contains(String.valueOf(c))){
            return TipoAFD.SIMBOLO_SIMPLE;
        }
        if(c == '/'){
            return TipoAFD.COMENTARIOS_Y_SLASH;
        }
        if(OPERADORES.contains(String.valueOf(c))){
            return TipoAFD.OPERADOR_RELACIONAL;
        }
        return TipoAFD.NINGUNO;
    }
}
