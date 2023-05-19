package mx.ipn.escom.ScannerComponents;

public class Token {
    final TipoToken tipo;
    final String lexema;
    final Object literal;
    final int linea;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = linea;
    }

    public int getLinea() {
        return linea;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public String toString(){
        return tipo + " " + lexema + " " + (literal==null?"":literal.toString());
    }
}
