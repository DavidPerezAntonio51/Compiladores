package mx.ipn.escom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InterpreteTest {
    @Test
    void prueba1(){
        String source = """
                select distinct campo1, campo2, campo1 from tabla1
                """;
        Assertions.assertEquals("La consulta es válida",Interprete.ejecutar(source));
    }
    @Test
    void prueba2(){
        String source = """
                select * from tabla1, tabla2 from tabla3
                """;
        Assertions.assertEquals("La consulta no es válida",Interprete.ejecutar(source));
    }
    @Test
    void prueba3(){
        String source = """
                select * from tabla1 a, tabla2 b, tabla3 c, tabla4 d
                """;
        Assertions.assertEquals("La consulta es válida",Interprete.ejecutar(source));
    }
    @Test
    void prueba4(){
        String source = """
                select campo1, campo2 from tabla1
                """;
        Assertions.assertEquals("La consulta es válida",Interprete.ejecutar(source));
    }
    @Test
    void prueba5(){
        String source = """
                select campo1, campo2, from tabla1
                """;
        Assertions.assertEquals("La consulta no es válida",Interprete.ejecutar(source));
    }
    @Test
    void prueba6(){
        String source = """
                select tabla1.campo1, tabla1.campo2 from tabla1
                """;
        Assertions.assertEquals("La consulta es válida",Interprete.ejecutar(source));
    }
}
