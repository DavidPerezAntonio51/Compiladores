package mx.ipn.escom.ParserComponents;

import mx.ipn.escom.ScannerComponents.TipoToken;
import mx.ipn.escom.ScannerComponents.Token;

import java.util.List;
import java.util.NoSuchElementException;

public class ParserImpl implements Parser{
    private final List<Token> tokens;
    private Token currentToken;

    public ParserImpl(List<Token> tokens){
        this.tokens = tokens;
        this.currentToken = tokens.get(0);
    }

    public void parse(){
        try {
            parse_program();
            match(TipoToken.EOF);
            System.out.println("Programa válido");
        } catch (ParseException e) {
            System.out.println("Programa no válido");
            System.err.println(e.getMessage());
        }
    }

    void parse_program() throws ParseException{
        parse_declaration();
    }

    void parse_declaration() throws ParseException{
        switch(currentToken.getTipo()) {
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
            default:
                // parse_statement() y otros casos se implementarán aquí
                break;
        }
    }

    void parse_class_decl() throws ParseException{
        match(TipoToken.CLASS);
        match(TipoToken.IDENTIFICADOR);
        parse_class_inher();
        match(TipoToken.LLAVE_IZQUIERDA);
        parse_functions();
        match(TipoToken.LLAVE_DERECHA);
    }

    void parse_class_inher() throws ParseException{
        if (currentToken.getTipo() == TipoToken.MENOR_QUE) {
            match(TipoToken.MENOR_QUE);
            match(TipoToken.IDENTIFICADOR);
        }
        // si no es MENOR_QUE, asumimos épsilon
    }

    void parse_functions() throws ParseException{
        if (currentToken.getTipo() == TipoToken.FUNCTION) {
            parse_function();
            parse_functions();
        }
        // si no es FUNCTION, asumimos épsilon
    }

    void parse_function() throws ParseException{
        match(TipoToken.FUNCTION);
        match(TipoToken.IDENTIFICADOR);
        match(TipoToken.PARENTESIS_IZQUIERDO);
        parse_parameters_opc();
        match(TipoToken.PARENTESIS_DERECHO);
        parse_block();
    }

    void parse_parameters_opc() throws ParseException{
        if (currentToken.getTipo() == TipoToken.IDENTIFICADOR) {
            parse_parameters();
        }
        // si no es IDENTIFICADOR, asumimos épsilon
    }

    void parse_parameters() throws ParseException{
        match(TipoToken.IDENTIFICADOR);
        parse_parameters_2();
    }

    void parse_parameters_2() throws ParseException{
        if (currentToken.getTipo() == TipoToken.COMA) {
            match(TipoToken.COMA);
            match(TipoToken.IDENTIFICADOR);
            parse_parameters_2();
        }
        // si no es COMA, asumimos épsilon
    }

    void parse_fun_decl() throws ParseException{
        match(TipoToken.FUNCTION);
        parse_function();
    }

    void parse_var_decl() throws ParseException{
        match(TipoToken.VAR);
        match(TipoToken.IDENTIFICADOR);
        parse_var_init();
        match(TipoToken.PUNTO_Y_COMA);
    }

    void parse_var_init() throws ParseException{
        if (currentToken.getTipo() == TipoToken.ASIGNAR) {
            match(TipoToken.ASIGNAR);
            parse_expression();
        }
        // si no es ASIGNAR, asumimos épsilon
    }

    void parse_statement() throws ParseException{
        switch(currentToken.getTipo()) {
            case PARENTESIS_IZQUIERDO: case NUMERO: case CADENA: case IDENTIFICADOR:
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
        parse_declaration();
    }

    void parse_expr_stmt()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_for_stmt() throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_for_stmt_1()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_for_stmt_2()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_for_stmt_3()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_if_stmt()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_else_statement()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_print_stmt()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_return_stmt()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_return_exp_opc()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_while_stmt()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_block()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_block_decl()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_expression()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_assignment()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_assignment_opc()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_logic_or()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_logic_or_2()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_logic_and()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_logic_and_2()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_equality()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_equality_2()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_comparison()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_comparison_2()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_term()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_term_2()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_factor()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_factor_2()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_unary()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_call()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_call_2()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_call_opc()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_primary()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_arguments_opc()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_arguments()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void parse_arguments_2()  throws ParseException{
        // TODO: implementar según la gramática
    }

    void match(TipoToken type) throws ParseException{
        if(currentToken.getTipo() == type){
            avanzar();
        } else {
            throw new ParseException("Error en la línea " + currentToken.getLinea() + ". Se esperaba un " + type + " pero se encontró un " + currentToken.getTipo());
        }
    }

    void avanzar(){
        try{
            currentToken = tokens.get(tokens.indexOf(currentToken) + 1);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException("Se llegó al final de los tokens sin encontrar EOF.");
        }
    }
}
