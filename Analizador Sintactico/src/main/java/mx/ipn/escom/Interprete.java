package mx.ipn.escom;

import mx.ipn.escom.ParserComponents.Parser;
import mx.ipn.escom.ParserComponents.ParserASD;
import mx.ipn.escom.ScannerComponents.Escaner;
import mx.ipn.escom.ScannerComponents.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Interprete {
    static boolean existenErrores = false;

    public static void main(String[] args) throws IOException {
        if(args.length > 1) {
            System.out.println("Uso correcto: interprete [script]");

            // Convención defininida en el archivo "system.h" de UNIX
            System.exit(64);
        } else if(args.length == 1){
            ejecutarArchivo(args[0]);
        } else{
            ejecutarPrompt();
        }
    }

    private static void ejecutarArchivo(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ejecutar(new String(bytes, Charset.defaultCharset()));

        // Se indica que existe un error
        if(existenErrores) System.exit(65);
    }

    private static void ejecutarPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;){
            System.out.print(">>> ");
            String linea = reader.readLine();
            if(linea == null) break; // Presionar Ctrl + D
            ejecutar(linea);
            existenErrores = false;
        }
    }

    public static String ejecutar(String source){
        Escaner scanner = new Escaner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new ParserASD(tokens);
        parser.parse();
        if (parser.esValido()) {
            System.out.println("La consulta es válida.");
            return "La consulta es válida";
        } else {
            System.out.println("La consulta no es válida.");
            return "La consulta no es válida";
        }
    }

    /*
    El método error se puede usar desde las distintas clases
    para reportar los errores:
    Interprete.error(....);
     */
    public static void error(int linea, String mensaje){
        reportar(linea, "", mensaje);
    }
    public static void error(int linea, String donde, String mensaje){
        reportar(linea, donde, mensaje);
    }

    private static void reportar(int linea, String donde, String mensaje){
        System.err.println(
                "[linea " + linea + "] Error " + donde + ": " + mensaje
        );
        existenErrores = true;
    }

}

