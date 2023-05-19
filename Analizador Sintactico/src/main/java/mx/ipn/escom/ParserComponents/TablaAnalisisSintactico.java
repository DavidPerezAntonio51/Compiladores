package mx.ipn.escom.ParserComponents;

import mx.ipn.escom.ScannerComponents.TipoToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TablaAnalisisSintactico {
    private static final Map<NoTerminales, Map<TipoToken, Integer>> tabla;

    static {
        tabla = new HashMap<>();

        tabla.put(NoTerminales.Q, new HashMap<>() {{
            put(TipoToken.SELECT, 1);
        }});
        tabla.put(NoTerminales.D, new HashMap<>() {{
            put(TipoToken.DISTINCT, 2);
            put(TipoToken.IDENTIFICADOR, 3);
            put(TipoToken.ASTERISCO, 3);
        }});
        tabla.put(NoTerminales.P, new HashMap<>() {{
            put(TipoToken.ASTERISCO, 4);
            put(TipoToken.IDENTIFICADOR, 5);
        }});
        tabla.put(NoTerminales.A, new HashMap<>() {{
            put(TipoToken.IDENTIFICADOR, 6);
        }});
        tabla.put(NoTerminales.A1, new HashMap<>() {{
            put(TipoToken.COMA, 7);
            put(TipoToken.FROM, 8);
        }});
        tabla.put(NoTerminales.A2, new HashMap<>() {{
            put(TipoToken.IDENTIFICADOR, 9);
        }});
        tabla.put(NoTerminales.A3, new HashMap<>() {{
            put(TipoToken.PUNTO, 10);
            put(TipoToken.COMA, 11);
            put(TipoToken.FROM, 11);
        }});
        tabla.put(NoTerminales.T, new HashMap<>() {{
            put(TipoToken.IDENTIFICADOR, 12);
        }});
        tabla.put(NoTerminales.T1, new HashMap<>() {{
            put(TipoToken.COMA, 13);
            put(TipoToken.EOF, 14);
        }});
        tabla.put(NoTerminales.T2, new HashMap<>() {{
            put(TipoToken.IDENTIFICADOR, 15);
        }});
        tabla.put(NoTerminales.T3, new HashMap<>() {{
            put(TipoToken.IDENTIFICADOR, 16);
            put(TipoToken.COMA, 17);
            put(TipoToken.EOF, 17);
        }});
    }

    public Optional<Integer> getRegla(NoTerminales noTerminal, TipoToken token) {
        Map<TipoToken, Integer> mapa = tabla.get(noTerminal);
        if (mapa == null) {
            //Enviamos vacio si no se encuentra coincidencia en la tabla de analisis sintactico
            return Optional.empty();
        }
        if(mapa.containsKey(token)){
            return Optional.of(mapa.get(token));
        }
        return Optional.empty();
    }
}
