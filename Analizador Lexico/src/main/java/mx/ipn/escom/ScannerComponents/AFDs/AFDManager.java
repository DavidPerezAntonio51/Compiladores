package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.CharArrayManager;

import java.util.Optional;


public class AFDManager {
    protected static final String LETRAS = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    protected static final String NUMEROS = "0123456789";
    protected static final String SIMBOLOS_SIMPLES = "+-/*()[]{};.,";
    private Identificador identificador;
    private Cadenas cadenas;
    private Numeros numeros;
    private Simbolos simbolos;

    public AFDManager(CharArrayManager manager) {
        identificador = new Identificador(manager);
        cadenas = new Cadenas(manager);
        numeros = new Numeros(manager);
        simbolos = new Simbolos(manager);

    }

    public Optional<Automata> getAFD(TipoAFD type) {
        return switch (type){
            case IDENTIFICADOR->Optional.of(this.identificador);
            case CADENA -> Optional.of(this.cadenas);
            case NUMEROS -> Optional.of(this.numeros);
            case SIMBOLO_SIMPLE -> Optional.of(this.simbolos);
            case OPERADOR_RELACIONAL -> null;
            case NINGUNO -> Optional.empty();
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
        return TipoAFD.NINGUNO;
    }
}
