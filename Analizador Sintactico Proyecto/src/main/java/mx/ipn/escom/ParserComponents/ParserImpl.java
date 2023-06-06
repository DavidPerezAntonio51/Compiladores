package mx.ipn.escom.ParserComponents;

import mx.ipn.escom.ScannerComponents.TipoToken;
import mx.ipn.escom.ScannerComponents.Token;

import java.util.List;
import java.util.NoSuchElementException;

public class ParserImpl implements Parser {
    private final List<Token> tokens;
    private Token currentToken;

    public ParserImpl(List<Token> tokens) {
        this.tokens = tokens;
        this.currentToken = tokens.get(0);
    }

    public String parse() {
        try {
            program();
            match(TipoToken.EOF);
            System.out.println("Programa válido");
            return"Programa válido";
        } catch (ParseException e) {
            System.out.println("Programa no válido");
            System.err.println(e.getMessage());
            return "Programa no válido";
        }
    }

    void program() throws ParseException {
        declaration();
    }

    void declaration() throws ParseException {
        switch (currentToken.getTipo()) {
            case CLASS:
                class_decl();
                declaration();
                break;
            case FUNCTION:
                fun_decl();
                declaration();
                break;
            case VAR:
                var_decl();
                declaration();
                break;
            case FOR:
            case IF:
            case PRINT:
            case RETURN:
            case WHILE:
            case LLAVE_IZQUIERDA:
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case NEGACION:
            case RESTA:
                statement();
                declaration();
                break;
            default:
                //Asumimos epsilon

        }
    }

    void class_decl() throws ParseException {
        match(TipoToken.CLASS);
        match(TipoToken.IDENTIFICADOR);
        class_inher();
        match(TipoToken.LLAVE_IZQUIERDA);
        functions();
        match(TipoToken.LLAVE_DERECHA);
    }

    void class_inher() throws ParseException {
        if (currentToken.getTipo() == TipoToken.MENOR_QUE) {
            match(TipoToken.MENOR_QUE);
            match(TipoToken.IDENTIFICADOR);
        }
        // si no es MENOR_QUE, asumimos épsilon
    }

    void functions() throws ParseException {
        if (currentToken.getTipo() == TipoToken.IDENTIFICADOR) {
            function();
            functions();
        }
        // si no es IDENTIFICADOR, asumimos épsilon
    }

    void function() throws ParseException {
        match(TipoToken.IDENTIFICADOR);
        match(TipoToken.PARENTESIS_IZQUIERDO);
        parameters_opc();
        match(TipoToken.PARENTESIS_DERECHO);
        block();
    }

    void parameters_opc() throws ParseException {
        if (currentToken.getTipo() == TipoToken.IDENTIFICADOR) {
            parameters();
        }
        // si no es IDENTIFICADOR, asumimos épsilon
    }

    void parameters() throws ParseException {
        match(TipoToken.IDENTIFICADOR);
        parameters_2();
    }

    void parameters_2() throws ParseException {
        if (currentToken.getTipo() == TipoToken.COMA) {
            match(TipoToken.COMA);
            match(TipoToken.IDENTIFICADOR);
            parameters_2();
        }
        // si no es COMA, asumimos épsilon
    }

    void fun_decl() throws ParseException {
        match(TipoToken.FUNCTION);
        function();
    }

    void var_decl() throws ParseException {
        match(TipoToken.VAR);
        match(TipoToken.IDENTIFICADOR);
        var_init();
        match(TipoToken.PUNTO_Y_COMA);
    }

    void var_init() throws ParseException {
        if (currentToken.getTipo() == TipoToken.ASIGNAR) {
            match(TipoToken.ASIGNAR);
            expression();
        }
        // si no es ASIGNAR, asumimos épsilon
    }

    void statement() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                expr_stmt();
                break;
            case FOR:
                for_stmt();
                break;
            case IF:
                if_stmt();
                break;
            case PRINT:
                print_stmt();
                break;
            case RETURN:
                return_stmt();
                break;
            case WHILE:
                while_stmt();
                break;
            case LLAVE_IZQUIERDA:
                block();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba el inicio de una declaración o sentencia pero se encontró un " + currentToken.getTipo());
        }
    }

    void expr_stmt() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                expression();
                match(TipoToken.PUNTO_Y_COMA);
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void for_stmt() throws ParseException {
        match(TipoToken.FOR);
        match(TipoToken.PARENTESIS_IZQUIERDO);
        for_stmt_1();
        for_stmt_2();
        for_stmt_3();
        match(TipoToken.PARENTESIS_DERECHO);
        statement();
    }

    void for_stmt_1() throws ParseException {
        switch (currentToken.getTipo()) {
            case VAR:
                var_decl();
                break;
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                expr_stmt();
                break;
            case PUNTO_Y_COMA:
                match(TipoToken.PUNTO_Y_COMA);
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void for_stmt_2() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                expression();
                match(TipoToken.PUNTO_Y_COMA);
                break;
            case PUNTO_Y_COMA:
                match(TipoToken.PUNTO_Y_COMA);
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void for_stmt_3() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                expression();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void if_stmt() throws ParseException {
        match(TipoToken.IF);
        match(TipoToken.PARENTESIS_IZQUIERDO);
        expression();
        match(TipoToken.PARENTESIS_DERECHO);
        statement();
        else_statement();
    }

    void else_statement() throws ParseException {
        if(currentToken.getTipo()==TipoToken.ELSE){
            match(TipoToken.ELSE);
            statement();
        }
        //Asumimos epsilon
    }

    void print_stmt() throws ParseException {
        match(TipoToken.PRINT);
        expression();
        match(TipoToken.PUNTO_Y_COMA);
    }

    void return_stmt() throws ParseException {
        match(TipoToken.RETURN);
        return_exp_opc();
        match(TipoToken.PUNTO_Y_COMA);
    }

    void return_exp_opc() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                expression();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void while_stmt() throws ParseException {
        match(TipoToken.WHILE);
        match(TipoToken.PARENTESIS_IZQUIERDO);
        expression();
        match(TipoToken.PARENTESIS_DERECHO);
        statement();
    }

    void block() throws ParseException {
        match(TipoToken.LLAVE_IZQUIERDA);
        block_decl();
        match(TipoToken.LLAVE_DERECHA);
    }

    void block_decl() throws ParseException {
        switch (currentToken.getTipo()) {
            case CLASS:
            case FUNCTION:
            case VAR:
            case FOR:
            case IF:
            case PRINT:
            case RETURN:
            case WHILE:
            case LLAVE_IZQUIERDA:
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                declaration();
                block_decl();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void expression() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                assignment();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void assignment() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                logic_or();
                assignment_opc();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void assignment_opc() throws ParseException {
        if (currentToken.getTipo() == TipoToken.ASIGNAR) {
            match(TipoToken.ASIGNAR);
            expression();
        }
        //Asumimos epsilon
    }

    void logic_or() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                logic_and();
                logic_or_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void logic_or_2() throws ParseException {
        if (currentToken.getTipo() == TipoToken.OR) {
            match(TipoToken.OR);
            logic_and();
            logic_or_2();
        }
        //Asumimos epsilon
    }

    void logic_and() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                equality();
                logic_and_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void logic_and_2() throws ParseException {
        if (currentToken.getTipo() == TipoToken.AND) {
            match(TipoToken.AND);
            equality();
            logic_and_2();
        }
        //Asumimos epsilon
    }

    void equality() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                comparison();
                equality_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void equality_2() throws ParseException {
        switch (currentToken.getTipo()) {
            case DIFERENTE:
                match(TipoToken.DIFERENTE);
                comparison();
                equality_2();
                break;
            case IGUAL_A:
                match(TipoToken.IGUAL_A);
                comparison();
                equality_2();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void comparison() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                term();
                comparison_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void comparison_2() throws ParseException {
        switch (currentToken.getTipo()) {
            case MAYOR_QUE:
                match(TipoToken.MAYOR_QUE);
                term();
                comparison_2();
                break;
            case MAYOR_O_IGUAL:
                match(TipoToken.MAYOR_O_IGUAL);
                term();
                comparison_2();
                break;
            case MENOR_QUE:
                match(TipoToken.MENOR_QUE);
                term();
                comparison_2();
                break;
            case MENOR_O_IGUAL:
                match(TipoToken.MENOR_O_IGUAL);
                term();
                comparison_2();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void term() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                factor();
                term_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void term_2() throws ParseException {
        switch (currentToken.getTipo()) {
            case RESTA:
                match(TipoToken.RESTA);
                factor();
                term_2();
                break;
            case SUMA:
                match(TipoToken.SUMA);
                factor();
                term_2();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void factor() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                unary();
                factor_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void factor_2() throws ParseException {
        switch (currentToken.getTipo()){
            case DIVISION:
                match(TipoToken.DIVISION);
                unary();
                factor_2();
                break;
            case MULTIPLICACION:
                match(TipoToken.MULTIPLICACION);
                unary();
                factor_2();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void unary() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
                call();
                break;
            case DIFERENTE:
                match(TipoToken.DIFERENTE);
                unary();
                break;
            case RESTA:
                match(TipoToken.RESTA);
                unary();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void call() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
                primary();
                call_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void call_2() throws ParseException {
        switch (currentToken.getTipo()) {
            case PARENTESIS_IZQUIERDO:
                match(TipoToken.PARENTESIS_IZQUIERDO);
                arguments_opc();
                match(TipoToken.PARENTESIS_DERECHO);
                call_2();
                break;
            case PUNTO:
                match(TipoToken.PUNTO);
                match(TipoToken.IDENTIFICADOR);
                call_2();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void call_opc() throws ParseException {
        // TODO: implementar según la gramática
    }

    void primary() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
                match(TipoToken.THIS);
                break;
            case SUPER:
                match(TipoToken.SUPER);
                match(TipoToken.PUNTO);
                match(TipoToken.IDENTIFICADOR);
                break;
            case FALSE:
                match(TipoToken.FALSE);
                break;
            case TRUE:
                match(TipoToken.TRUE);
                break;
            case NULL:
                match(TipoToken.NULL);
                break;
            case NUMERO:
                match(TipoToken.NUMERO);
                break;
            case CADENA:
                match(TipoToken.CADENA);
                break;
            case PARENTESIS_IZQUIERDO:
                match(TipoToken.PARENTESIS_IZQUIERDO);
                expression();
                match(TipoToken.PARENTESIS_DERECHO);
            case IDENTIFICADOR:
                match(TipoToken.IDENTIFICADOR);
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void arguments_opc() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                arguments();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void arguments() throws ParseException {
        switch (currentToken.getTipo()) {
            case THIS:
            case SUPER:
            case FALSE:
            case TRUE:
            case NULL:
            case NUMERO:
            case CADENA:
            case PARENTESIS_IZQUIERDO:
            case IDENTIFICADOR:
            case DIFERENTE:
            case RESTA:
                expression();
                arguments_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void arguments_2() throws ParseException {
        if (currentToken.getTipo() == TipoToken.COMA) {
            match(TipoToken.COMA);
            expression();
           arguments_2();
        }
        //Asumimos epsilon
    }

    void match(TipoToken type) throws ParseException {
        if (currentToken.getTipo() == type) {
            if(currentToken.getTipo()!=TipoToken.EOF)
                avanzar();
        } else {
            throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba un " + type + " pero se encontró un " + currentToken.getTipo());
        }
    }

    void avanzar() {
        try {
            currentToken = tokens.get(tokens.indexOf(currentToken) + 1);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException("Se llegó al final de los tokens sin encontrar EOF.");
        }
    }
}
