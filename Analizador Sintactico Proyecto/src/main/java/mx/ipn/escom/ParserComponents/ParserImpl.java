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
            parse_program();
            match(TipoToken.EOF);
            System.out.println("Programa válido");
            return"Programa válido";
        } catch (ParseException e) {
            System.out.println("Programa no válido");
            System.err.println(e.getMessage());
            return "Programa no válido";
        }
    }

    void parse_program() throws ParseException {
        parse_declaration();
    }

    void parse_declaration() throws ParseException {
        switch (currentToken.getTipo()) {
            case CLASS:
                parse_class_decl();
                parse_declaration();
                break;
            case FUNCTION:
                parse_fun_decl();
                parse_declaration();
                break;
            case VAR:
                parse_var_decl();
                parse_declaration();
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
                parse_statement();
                parse_declaration();
                break;
            default:
                //Asumimos epsilon

        }
    }

    void parse_class_decl() throws ParseException {
        match(TipoToken.CLASS);
        match(TipoToken.IDENTIFICADOR);
        parse_class_inher();
        match(TipoToken.LLAVE_IZQUIERDA);
        parse_functions();
        match(TipoToken.LLAVE_DERECHA);
    }

    void parse_class_inher() throws ParseException {
        if (currentToken.getTipo() == TipoToken.MENOR_QUE) {
            match(TipoToken.MENOR_QUE);
            match(TipoToken.IDENTIFICADOR);
        }
        // si no es MENOR_QUE, asumimos épsilon
    }

    void parse_functions() throws ParseException {
        if (currentToken.getTipo() == TipoToken.IDENTIFICADOR) {
            parse_function();
            parse_functions();
        }
        // si no es IDENTIFICADOR, asumimos épsilon
    }

    void parse_function() throws ParseException {
        match(TipoToken.IDENTIFICADOR);
        match(TipoToken.PARENTESIS_IZQUIERDO);
        parse_parameters_opc();
        match(TipoToken.PARENTESIS_DERECHO);
        parse_block();
    }

    void parse_parameters_opc() throws ParseException {
        if (currentToken.getTipo() == TipoToken.IDENTIFICADOR) {
            parse_parameters();
        }
        // si no es IDENTIFICADOR, asumimos épsilon
    }

    void parse_parameters() throws ParseException {
        match(TipoToken.IDENTIFICADOR);
        parse_parameters_2();
    }

    void parse_parameters_2() throws ParseException {
        if (currentToken.getTipo() == TipoToken.COMA) {
            match(TipoToken.COMA);
            match(TipoToken.IDENTIFICADOR);
            parse_parameters_2();
        }
        // si no es COMA, asumimos épsilon
    }

    void parse_fun_decl() throws ParseException {
        match(TipoToken.FUNCTION);
        parse_function();
    }

    void parse_var_decl() throws ParseException {
        match(TipoToken.VAR);
        match(TipoToken.IDENTIFICADOR);
        parse_var_init();
        match(TipoToken.PUNTO_Y_COMA);
    }

    void parse_var_init() throws ParseException {
        if (currentToken.getTipo() == TipoToken.ASIGNAR) {
            match(TipoToken.ASIGNAR);
            parse_expression();
        }
        // si no es ASIGNAR, asumimos épsilon
    }

    void parse_statement() throws ParseException {
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
                parse_expr_stmt();
                break;
            case FOR:
                parse_for_stmt();
                break;
            case IF:
                parse_if_stmt();
                break;
            case PRINT:
                parse_print_stmt();
                break;
            case RETURN:
                parse_return_stmt();
                break;
            case WHILE:
                parse_while_stmt();
                break;
            case LLAVE_IZQUIERDA:
                parse_block();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba el inicio de una declaración o sentencia pero se encontró un " + currentToken.getTipo());
        }
    }

    void parse_expr_stmt() throws ParseException {
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
                parse_expression();
                match(TipoToken.PUNTO_Y_COMA);
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_for_stmt() throws ParseException {
        match(TipoToken.FOR);
        match(TipoToken.PARENTESIS_IZQUIERDO);
        parse_for_stmt_1();
        parse_for_stmt_2();
        parse_for_stmt_3();
        match(TipoToken.PARENTESIS_DERECHO);
        parse_statement();
    }

    void parse_for_stmt_1() throws ParseException {
        switch (currentToken.getTipo()) {
            case VAR:
                parse_var_decl();
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
                parse_expr_stmt();
                break;
            case PUNTO_Y_COMA:
                match(TipoToken.PUNTO_Y_COMA);
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_for_stmt_2() throws ParseException {
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
                parse_expression();
                match(TipoToken.PUNTO_Y_COMA);
                break;
            case PUNTO_Y_COMA:
                match(TipoToken.PUNTO_Y_COMA);
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_for_stmt_3() throws ParseException {
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
                parse_expression();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void parse_if_stmt() throws ParseException {
        match(TipoToken.IF);
        match(TipoToken.PARENTESIS_IZQUIERDO);
        parse_expression();
        match(TipoToken.PARENTESIS_DERECHO);
        parse_statement();
        parse_else_statement();
    }

    void parse_else_statement() throws ParseException {
        if(currentToken.getTipo()==TipoToken.ELSE){
            match(TipoToken.ELSE);
            parse_statement();
        }
        //Asumimos epsilon
    }

    void parse_print_stmt() throws ParseException {
        match(TipoToken.PRINT);
        parse_expression();
        match(TipoToken.PUNTO_Y_COMA);
    }

    void parse_return_stmt() throws ParseException {
        match(TipoToken.RETURN);
        parse_return_exp_opc();
        match(TipoToken.PUNTO_Y_COMA);
    }

    void parse_return_exp_opc() throws ParseException {
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
                parse_expression();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void parse_while_stmt() throws ParseException {
        match(TipoToken.WHILE);
        match(TipoToken.PARENTESIS_IZQUIERDO);
        parse_expression();
        match(TipoToken.PARENTESIS_DERECHO);
        parse_statement();
    }

    void parse_block() throws ParseException {
        match(TipoToken.LLAVE_IZQUIERDA);
        parse_block_decl();
        match(TipoToken.LLAVE_DERECHA);
    }

    void parse_block_decl() throws ParseException {
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
                parse_declaration();
                parse_block_decl();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void parse_expression() throws ParseException {
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
                parse_assignment();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_assignment() throws ParseException {
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
                parse_logic_or();
                parse_assignment_opc();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_assignment_opc() throws ParseException {
        if (currentToken.getTipo() == TipoToken.ASIGNAR) {
            match(TipoToken.ASIGNAR);
            parse_expression();
        }
        //Asumimos epsilon
    }

    void parse_logic_or() throws ParseException {
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
                parse_logic_and();
                parse_logic_or_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_logic_or_2() throws ParseException {
        if (currentToken.getTipo() == TipoToken.OR) {
            match(TipoToken.OR);
            parse_logic_and();
            parse_logic_or_2();
        }
        //Asumimos epsilon
    }

    void parse_logic_and() throws ParseException {
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
                parse_equality();
                parse_logic_and_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_logic_and_2() throws ParseException {
        if (currentToken.getTipo() == TipoToken.AND) {
            match(TipoToken.AND);
            parse_equality();
            parse_logic_and_2();
        }
        //Asumimos epsilon
    }

    void parse_equality() throws ParseException {
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
                parse_comparison();
                parse_equality_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_equality_2() throws ParseException {
        switch (currentToken.getTipo()) {
            case DIFERENTE:
                match(TipoToken.DIFERENTE);
                parse_comparison();
                parse_equality_2();
                break;
            case IGUAL_A:
                match(TipoToken.IGUAL_A);
                parse_comparison();
                parse_equality_2();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void parse_comparison() throws ParseException {
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
                parse_term();
                parse_comparison_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_comparison_2() throws ParseException {
        switch (currentToken.getTipo()) {
            case MAYOR_QUE:
                match(TipoToken.MAYOR_QUE);
                parse_term();
                parse_comparison_2();
                break;
            case MAYOR_O_IGUAL:
                match(TipoToken.MAYOR_O_IGUAL);
                parse_term();
                parse_comparison_2();
                break;
            case MENOR_QUE:
                match(TipoToken.MENOR_QUE);
                parse_term();
                parse_comparison_2();
                break;
            case MENOR_O_IGUAL:
                match(TipoToken.MENOR_O_IGUAL);
                parse_term();
                parse_comparison_2();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void parse_term() throws ParseException {
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
                parse_factor();
                parse_term_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_term_2() throws ParseException {
        switch (currentToken.getTipo()) {
            case RESTA:
                match(TipoToken.RESTA);
                parse_factor();
                parse_term_2();
                break;
            case SUMA:
                match(TipoToken.SUMA);
                parse_factor();
                parse_term_2();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void parse_factor() throws ParseException {
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
                parse_unary();
                parse_factor_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_factor_2() throws ParseException {
        switch (currentToken.getTipo()){
            case DIVISION:
                match(TipoToken.DIVISION);
                parse_unary();
                parse_factor_2();
                break;
            case MULTIPLICACION:
                match(TipoToken.MULTIPLICACION);
                parse_unary();
                parse_factor_2();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void parse_unary() throws ParseException {
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
                parse_call();
                break;
            case DIFERENTE:
                match(TipoToken.DIFERENTE);
                parse_unary();
                break;
            case RESTA:
                match(TipoToken.RESTA);
                parse_unary();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_call() throws ParseException {
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
                parse_primary();
                parse_call_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_call_2() throws ParseException {
        switch (currentToken.getTipo()) {
            case PARENTESIS_IZQUIERDO:
                match(TipoToken.PARENTESIS_IZQUIERDO);
                parse_arguments_opc();
                match(TipoToken.PARENTESIS_DERECHO);
                parse_call_2();
                break;
            case PUNTO:
                match(TipoToken.PUNTO);
                match(TipoToken.IDENTIFICADOR);
                parse_call_2();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void parse_call_opc() throws ParseException {
        // TODO: implementar según la gramática
    }

    void parse_primary() throws ParseException {
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
                parse_expression();
                match(TipoToken.PARENTESIS_DERECHO);
            case IDENTIFICADOR:
                match(TipoToken.IDENTIFICADOR);
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_arguments_opc() throws ParseException {
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
                parse_arguments();
                break;
            default:
                //Asumimos epsilon
        }
    }

    void parse_arguments() throws ParseException {
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
                parse_expression();
                parse_arguments_2();
                break;
            default:
                throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba una expresion valida" + currentToken.getTipo());
        }
    }

    void parse_arguments_2() throws ParseException {
        if (currentToken.getTipo() == TipoToken.COMA) {
            match(TipoToken.COMA);
            parse_expression();
            parse_arguments_2();
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
