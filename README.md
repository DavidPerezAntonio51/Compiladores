# Compiladores

ESCOM IPN Compiladores con el profesdor Gabriel de Jésus

## Como usar

Primero asegurese de estar en la carpeta correspondiente al proyecto que desee ejecutar `/Analizador Lexico` ó `/Analizador Sintatico`

Compile el ejecutable con su IDE favorito, o desde Powershell ó CMD con el comando `mvn clean package`.
A continuación dirijase a la carpeta target y ejecute `java -jar AnalizadorLexico-1.0-SNAPSHOT.jar`, esto desplegara el analizador lexico en modo prompt.
Si por otro lado desea usar un archivo fuente puede pasarlo de la siguiente manera `java -jar AnalizadorLexico-1.0-SNAPSHOT.jar <ruta del archivo entre comillas>`.

## Anlizador Léxico

Las palabras reservadas que puede detectar el analizador léxico son:

|Palabra|Equivalente|Descripción|
|---|---|---|
|clase|`class`|palabra para definir clases|
|fun|`function`|palabra para definir funciones|
|si|`if`|palabra para definir una condición|
|y|`and`|operador and (&&)|
|o|`or`|operador or (\|\|)|
|ademas|`else`|palabra para el caso en que no se cumpla la condicion if|
|falso|`false`|valor boleano|
|verdadero|`true`|valor boleano|
|para|`for`|palabra reservada para bucle|
|nulo|`null`|ausencia de valor|
|imprimir|`print`|palabra para una función de impresión|
|retornar|`return`|retorno de un valor|
|super|`super`|llamada a una clase padre|
|este|`this`|referencia a si mismo|
|var|`var`|declaracion de una variable|
|mientras|`while`|declaracion de un ciclo|

Lista de tokens detectados en el analizador léxico:

| Token | Lexema |
| --- | --- |
| `AND` | y |
| `CLASS` | clase |
| `ELSE` | ademas |
| `FALSE` | falso |
| `FOR` | para |
| `FUNCTION` | fun |
| `IF` | si |
| `NULL` | nulo |
| `OR` | o |
| `PRINT` | imprimir |
| `RETURN` | retornar |
| `SUPER` | super |
| `THIS` | este |
| `TRUE` | verdadero |
| `VAR` | var |
| `WHILE` | mientras |
| `COMENTARIOS` | //Cualquier texto o caracter despues de doble slash |
| `COMENTARIOS` | /\*Cualquier texto o caracter entre slash-asterisco--asterisco-slash\*/ |
| `EOF` | no tiene lexema, se agrega automaticamente al final del analisis |
| `PARENTESIS_IZQUEIRDO` | ( |
| `PARENTESIS_DERECHO` | ) |
| `CORCHETE_IZQUIERDO` | [ |
| `CORCHETE_DERECHO` | ] |
| `LLAVE_IZQUIERDA` | { |
| `LLAVE_DERECHA` | } |
| `COMA` | , |
| `PUNTO` | . |
| `PUNTO_Y_COMA` | ; |
| `SUMA` | + |
| `RESTA` | - |
| `MULTIPLICACION` | * |
| `DIVISION` | / |
| `NEGACION` | ! |
| `DIFERENTE` | != |
| `ASIGNAR` | = |
| `IGUAL_A` | == |
| `MENOR_QUE` | < |
| `MENOR_O_IGUAL` | <= |
| `MAYOR_QUE` | > |
| `MAYOR_O_IGUAL` | >= |
| `IDENTIFICADOR` | Cualquier texto que empiece con un caracter, no puede empezar con numero |
| `CADENA` | "Cualquier caracter entre comillas" |
| `NUMERO` | cualqueir numero entero, flotante o exponencial, positivos o negativos |

## Analizador Sintactico

En la carpeta `/Analizador Sintctico` se encontrara un **Analizador Sintactico Descendente no recursivo** (predictivo), este analizador se centra en un fragmento de la gramatica de SQL, especificamente nos permite verificar la sintaxis de una sentencia `SELECT`.

La gramatica con la que se va a trabajar esta definida de la siguiente manera:

Nota: $\epsilon$ equivale a cadena vacia

1. **$Q$** -> `select` **$D$** `from` **$T$**
2. **$D$** -> `distinct` **$P$**
3. **$D$** -> **$P$**
4. **$P$** -> `*`
5. **$P$** -> **$A$**
6. **$A$** -> **$A_2A_1$**
7. **$A_1$** -> `,`**$A$**
8. **$A_1$** -> $\epsilon$
9. **$A_2$** -> `id`$A_3$
10. **$A_3$** -> `.` `id`
11. **$A_3$** -> $\epsilon$
12. **T** -> **$T_2T_1$**
13. **$T_1$** -> `,` **$T$**
14. **$T_1$** -> $\epsilon$
15. **$T_2$** -> `id`**$T_3$**
16. **$T_3$** -> `id`
17. **$T_3$** -> $\epsilon$

se expandieron las reglas para ser usadas posteriormente en la tabla de analisis sintactico

Se ha reutilizado el analizador léxico con algunos cambios menores en los tokens que son detectados.

| Token | Lexema |
| --- | --- |
| `SELECT` | select |
| `FROM` | from |
| `DISTINCT` | distinct |
| `ASTERISCO` | * |
| `PUNTO` | . |
| `COMA` | , |
| `IDENTIFICADOR` | cualquier nombre de tabla |

Este analizador sintactico descendente utiliza la siguiente tabla de analisis sintactico, se dejaran 2 versiones de la tabla, una utilizando los indices de la produccion y otra con las producciones insertadas directamente, en ambos casos las casillas vacias representan estados de errores.

### Tabla de analisis sintactico con indices

|                 | SELECT   | DISTINCT  | *        | id       | .       | ,       | FROM    |
| --------------- | -------- | --------- | -------- | -------- | ------- | ------- | ------- |
| Q               | 1        |  `error`  |  `error` |  `error` | `error` | `error` | `error` |
| D               |  `error` | 2         | 3        | 3        | `error` | `error` | `error` |
| P               |  `error` |  `error`  | 4        | 5        | `error` | `error` | `error` |
| A               |  `error` |  `error`  |  `error` | 6        | `error` | `error` | `error` |
| A1              |  `error` |  `error`  |  `error` |  `error` | `error` | 7       | 8       |
| A2              |  `error` |  `error`  |  `error` | 9        | `error` | `error` | `error` |
| A3              |  `error` |  `error`  |  `error` |  `error` | 10      | 11      | 11      |
| T               |  `error` |  `error`  |  `error` | 12       | `error` | `error` | `error` |
| T1              |  `error` |  `error`  |  `error` |  `error` | `error` | 13      | 14      |
| T2              |  `error` |  `error`  |  `error` | 15       | `error` | `error` | `error` |
| T3              |  `error` |  `error`  |  `error` |  `error` | `error` | 16      | 17      |

### Tabla de analisis sintactico con producciones directas

|                 | SELECT   | DISTINCT  | *        | id       | .     | ,     | FROM  |
| --------------- | -------- | --------- | -------- | -------- | ----- | ----- | ----- |
| Q               | `select` **$D$** `from` **$T$** |           |          |          |       |       |       |
| D               |          | `distinct` **$P$** | **$P$** | **$P$** |       |       |       |
| P               |          |           | `*` | **$A$** |       |       |       |
| A               |          |           |          | **$A_2A_1$** |       |       |       |
| A1              |          |           |          |          |       | `,`**$A$** | $\epsilon$ |
| A2              |          |           |          | `id`$A_3$ |       |       |       |
| A3              |          |           |          |          | `.` `id` | $\epsilon$ | $\epsilon$ |
| T               |          |           |          | **$T_2T_1$** |       |       |       |
| T1              |          |           |          |          |       | `,` **$T$** | $\epsilon$ |
| T2              |          |           |          | `id`**$T_3$** |       |       |       |
| T3              |          |           |          |          |       | `id` | $\epsilon$ |
