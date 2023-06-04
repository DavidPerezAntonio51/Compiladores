package mx.ipn.escom.ScannerComponents;

import mx.ipn.escom.Interprete;
import mx.ipn.escom.ScannerComponents.AFDs.AFDManager;
import mx.ipn.escom.ScannerComponents.AFDs.Automata;
import mx.ipn.escom.ScannerComponents.AFDs.Comentarios;
import mx.ipn.escom.ScannerComponents.AFDs.TipoAFD;

import java.util.*;

public class Escaner {
    private final String source;

    private final CharArrayManager manager;
    private final AFDManager AUTOMATAS;

    private final List<Token> tokens;

    public static int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("y", TipoToken.AND);
        palabrasReservadas.put("clase", TipoToken.CLASS);
        palabrasReservadas.put("ademas", TipoToken.ELSE);
        palabrasReservadas.put("falso", TipoToken.FALSE);
        palabrasReservadas.put("para", TipoToken.FOR);
        palabrasReservadas.put("fun", TipoToken.FUNCTION); //definir funciones
        palabrasReservadas.put("si", TipoToken.IF);
        palabrasReservadas.put("nulo", TipoToken.NULL);
        palabrasReservadas.put("o", TipoToken.OR);
        palabrasReservadas.put("imprimir", TipoToken.PRINT);
        palabrasReservadas.put("retornar", TipoToken.RETURN);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("este", TipoToken.THIS);
        palabrasReservadas.put("verdadero", TipoToken.TRUE);
        palabrasReservadas.put("var", TipoToken.VAR); //definir variables
        palabrasReservadas.put("mientras", TipoToken.WHILE);
    }

    public Escaner(String source) {
        this.source = source;
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
                        if (automata.get().getTipoToken() != TipoToken.COMENTARIOS) {
                            tokens.add(new Token(automata.get().getTipoToken(), lexema.get(), automata.get().getLiteral(), linea));
                        }else{
                            Comentarios comentarios = (Comentarios) automata.get();
                            linea+=comentarios.getAumento();
                        }
                    }
                } else {
                    String mensaje = switch (automata.get().getTipoToken()) {
                        case CADENA -> "Cadena mal introducida";
                        case NUMERO -> "Numero mal introducido";
                        case COMENTARIOS -> "Comentario mal escrito";
                        default ->
                                throw new IllegalStateException("Unexpected value: " + automata.get().getTipoToken());
                    };
                    Interprete.error(linea, mensaje);
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