package mx.ipn.escom.ScannerComponents;

import mx.ipn.escom.ScannerComponents.AFDs.AFDManager;
import mx.ipn.escom.ScannerComponents.AFDs.Automata;
import mx.ipn.escom.ScannerComponents.AFDs.TipoAFD;

import java.util.*;

public class Escaner {

    private final CharArrayManager manager;
    private final AFDManager AUTOMATAS;

    private final List<Token> tokens;

    public static int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("select", TipoToken.SELECT);
        palabrasReservadas.put("from", TipoToken.FROM);
        palabrasReservadas.put("distinct", TipoToken.DISTINCT);
    }

    public Escaner(String source) {
        this.tokens = new ArrayList<>();
        manager = new CharArrayManager(source.toCharArray());
        AUTOMATAS = new AFDManager(manager);
    }

    public List<Token> scanTokens() {
        //Aquí va el corazón del scanner.
        while (manager.hasNext()) {
            char nextChar = manager.getNextChar();
            TipoAFD type = AUTOMATAS.checkType(nextChar);
            Optional<Automata> automata = AUTOMATAS.getAFD(type);
            if (automata.isPresent()) {
                manager.backPosition();
                Optional<String> lexema = automata.get().getLexema();
                if (lexema.isPresent()) {
                    if (palabrasReservadas.containsKey(lexema.get())) {
                        tokens.add(new Token(palabrasReservadas.get(lexema.get()), lexema.get(), automata.get().getLiteral(), linea));
                    } else {
                        tokens.add(new Token(automata.get().getTipoToken(), lexema.get(), automata.get().getLiteral(), linea));
                    }
                }
            }
            if (nextChar == '\n') {
                linea++;
            }
        }
        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        tokens.add(new Token(TipoToken.EOF, "", null, linea));

        return tokens;
    }


}

/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */