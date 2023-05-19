package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.TipoToken;

import java.util.Optional;

public interface Automata {
    Optional<String> getLexema();
    TipoToken getTipoToken();

    Object getLiteral();
}
