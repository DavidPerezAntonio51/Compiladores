package mx.ipn.escom.ScannerComponents.AFDs;

import mx.ipn.escom.ScannerComponents.TipoToken;

public interface Automata {
    public String getLexema();
    public TipoToken getTipoToken();

    public Object getLiteral();
}
