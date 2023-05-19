package mx.ipn.escom.ParserComponents;

import mx.ipn.escom.Interprete;
import mx.ipn.escom.ScannerComponents.TipoToken;
import mx.ipn.escom.ScannerComponents.Token;

import java.util.*;

public class ParserASD implements Parser{
    private final List<Token> tokens;
    private final Stack<Object> pila;
    private final TablaAnalisisSintactico tabla;
    private boolean consultaValida;

    public ParserASD(List<Token> tokens){
        this.tokens = tokens;
        this.pila = new Stack<>();
        this.tabla = new TablaAnalisisSintactico();
        this.consultaValida = true;
    }

    @Override
    public void parse() {
        int tokenIndex = 0;
        pila.push(TipoToken.EOF);
        pila.push(NoTerminales.Q);

        while (!pila.isEmpty()) {
            Object top = pila.peek();
            Token tokenActual = tokens.get(tokenIndex);
            if (top instanceof TipoToken token && tokenActual.getTipo().equals(token)) {
                pila.pop();
                tokenIndex++;
            } else if (top instanceof NoTerminales noTerminal) {
                Optional<Integer> regla = tabla.getRegla(noTerminal, tokenActual.getTipo());
                if (regla.isPresent()) {
                    expandirPilaConRegla(regla.get());
                } else {
                    mostrarError(noTerminal, tokenActual);
                    consultaValida = false;
                    break;
                }
            } else {
                throw new IllegalStateException("La pila contiene un objeto desconocido: " + top);
            }
        }
    }

    private void expandirPilaConRegla(int regla) {
        pila.pop();
        // Agregar a la pila los símbolos del lado derecho de la regla de producción en orden inverso
        switch (regla) {
            case 1:
                pila.push(NoTerminales.T);
                pila.push(TipoToken.FROM);
                pila.push(NoTerminales.D);
                pila.push(TipoToken.SELECT);
                break;
            case 2:
                pila.push(NoTerminales.P);
                pila.push(TipoToken.DISTINCT);
                break;
            case 3:
                pila.push(NoTerminales.P);
                break;
            case 4:
                pila.push(TipoToken.ASTERISCO);
                break;
            case 5:
                pila.push(NoTerminales.A);
                break;
            case 6:
                pila.push(NoTerminales.A1);
                pila.push(NoTerminales.A2);
                break;
            case 7:
                pila.push(NoTerminales.A);
                pila.push(TipoToken.COMA);
                break;
            case 8:

                break;
            case 9:
                pila.push(NoTerminales.A3);
                pila.push(TipoToken.IDENTIFICADOR);
                break;
            case 10:
                pila.push(TipoToken.IDENTIFICADOR);
                pila.push(TipoToken.PUNTO);
                break;
            case 11:

                break;
            case 12:
                pila.push(NoTerminales.T1);
                pila.push(NoTerminales.T2);
                break;
            case 13:
                pila.push(NoTerminales.T);
                pila.push(TipoToken.COMA);
                break;
            case 14:

                break;
            case 15:
                pila.push(NoTerminales.T3);
                pila.push(TipoToken.IDENTIFICADOR);
                break;
            case 16:
                pila.push(TipoToken.IDENTIFICADOR);
                break;
            case 17:

                break;
            default:
                break;
        }
    }

    @Override
    public boolean esValido() {
        return consultaValida;
    }

    private void mostrarError(NoTerminales esperado, Token actual) {
        List<String> expected = switch (esperado){
            case Q -> List.of(TipoToken.SELECT.toString());
            case D -> List.of(TipoToken.DISTINCT.toString(), "'*'", TipoToken.IDENTIFICADOR.toString());
            case P -> List.of("'*'",TipoToken.IDENTIFICADOR.toString());
            case A, A2, T, T2 -> List.of(TipoToken.IDENTIFICADOR.toString());
            case A1, T1, T3 -> List.of("','",TipoToken.FROM.toString());
            case A3 -> List.of("'.'", "','", TipoToken.FROM.toString());
        };
        Interprete.error(actual.getLinea(), actual.getLexema(),"No se esperaba el token " + actual.getTipo() + ": Se esperaba alguno de los siguientes simbolos: " + expected);
        //System.out.println("Error sintáctico en la línea " + actual.getLinea() + ": Símbolo esperado: " + esperado + ", Token dado: " + actual.getTipo());
    }
}
