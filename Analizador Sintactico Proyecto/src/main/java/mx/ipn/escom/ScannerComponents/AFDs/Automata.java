package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.TipoToken;

import java.util.Optional;

public interface Automata {
    public Optional<String> getLexema();
    public TipoToken getTipoToken();

    public Object getLiteral();
}
