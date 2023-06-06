package mx.ipn.escom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InterpreteTest {
    @Test
    void prueba1(){
        String source = """
                var a;
                var b=0;
                """;
        Assertions.assertEquals("Programa válido",Interprete.ejecutar(source));

    }
    @Test
    void prueba2(){
        String source = """
                si(a<123){
                    imprimir a;
                }
                """;
        Assertions.assertEquals("Programa válido",Interprete.ejecutar(source));
    }
    @Test
    void prueba3(){
        String source = """
                para(var i=0; ;){
                     imprimir i;
                 
                     si (i>100){
                         retornar;
                     }
                 }
                """;
        Assertions.assertEquals("Programa válido",Interprete.ejecutar(source));
    }
    @Test
    void prueba4(){
        String source = """
                fun sumar(variable1, variable2){
                    retornar variabl1 + variable2;
                }
                """;
        Assertions.assertEquals("Programa válido",Interprete.ejecutar(source));
    }
    @Test
    void prueba5(){
        String source = """
                clase Perro < Animal {
                     ladrar(){
                         imprimir "Guauuu";
                     }
                 
                     comer(){
                         mientras(tanque < 100){
                             tanque = tanque + 1;
                         }
                     }
                 }
                 
                 fun crearPerro(){
                     perro = Perro();
                     perror.ladrar();
                 }
                 
                 crearPerro();
                """;
        Assertions.assertEquals("Programa válido",Interprete.ejecutar(source));
    }
    @Test
    void prueba6(){
        String source = """
                var nombre = "Hola mundo"
                fun presentarse(){
                    imprimir nombre;
                }
                """;
        Assertions.assertEquals("Programa no válido",Interprete.ejecutar(source));
    }
    @Test
    void prueba7(){
        String source = """
                /*
                Código para calcula la serie de Fibonacci
                */
                var fib = 0;
                var lim = 10;
                var aux = 1;
                                
                para(var init = 1; init <= lim; init = init + 1)
                    imprimir fib;
                    aux = aux + fib;
                    fib = aux - fib;
                }
                """;
        Assertions.assertEquals("Programa no válido",Interprete.ejecutar(source));
    }
}
