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

## Analizador Sintactico Práctica

En la carpeta `/Analizador Sintactico` se encontrara un **Analizador Sintactico Descendente no recursivo** (predictivo), este analizador se centra en un fragmento de la gramatica de SQL, especificamente nos permite verificar la sintaxis de una sentencia `SELECT`.

La gramatica con la que se va a trabajar esta definida de la siguiente manera:

Nota: $\epsilon$ equivale a cadena vacia

### Gramatica

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

## Analizador Sintactico Proyecto

En la carpeta `/Analizador Sintactico Proyecto` se encontrara un **Analizador Sintactico Descendente Recursivo** que se encargara del analisis de nuestro lenguaje de programación tomando como entrada la lista de tokens definidos en el analizador léxico.

Para este analizador necesitaremos el conjunto primero de la grámatica. La gramatica es la siguiente:

### Gramática proyecto final

**PROGRAM** -> **DECLARATION**

---

#### Declaraciones

**DECLARATION** -> **CLASS_DECL DECLARATION**  
                -> **FUN_DECL DECLARATION**  
                -> **VAR_DECL DECLARATION**  
                -> **STATEMENT DECLARATION**  
                -> `Ɛ`

**CLASS_DECL** -> `class` `id` **CLASS_INHER** `{` **FUNCTIONS** `}`

**CLASS_INHER** -> `<` **id**  
                -> `Ɛ`

**FUN_DECL** -> `fun` **FUNCTION**

**VAR_DECL** -> `var` `id` **VAR_INIT** `;`

**VAR_INIT** -> `=` **EXPRESSION**  
             -> `Ɛ`

---

#### Sentencias

**STATEMENT** -> **EXPR_STMT**  
-> **FOR_STMT**  
-> **IF_STMT**  
-> **PRINT_STMT**  
-> **RETURN_STMT**  
-> **WHILE_STMT**  
-> **BLOCK**

**EXPR_STMT** -> **EXPRESSION** `;`

**FOR_STMT** -> `for` `(` **FOR_STMT_1 FOR_STMT_2 FOR_STMT_3** `)` **STATEMENT**

**FOR_STMT_1** -> **VAR_DECL**  
-> **EXPR_STMT**  
-> `;`

**FOR_STMT_2** -> **EXPRESSION** `;`  
-> `;`

**FOR_STMT_3** -> **EXPRESSION**  
-> `Ɛ`

**IF_STMT** -> `if` `(`**EXPRESSION**`)` **STATEMENT ELSE_STATEMENT**

**ELSE_STATEMENT** -> `else` **STATEMENT**  
-> `Ɛ`

**PRINT_STMT** -> `print` **EXPRESSION** `;`

**RETURN_STMT** -> `return` **RETURN_EXP_OPC** `;`

**RETURN_EXP_OPC** -> **EXPRESSION**
-> `Ɛ`

**WHILE_STMT** -> `while` `(` **EXPRESSION** `)` **STATEMENT**

**BLOCK** -> `{` **BLOCK_DECL** `}`

**BLOCK_DECL** -> **DECLARATION BLOCK_DECL**  
-> `Ɛ`

---

#### Expresiones

**EXPRESSION** -> **ASSIGNMENT**

**ASSIGNMENT** -> **LOGIC_OR ASSIGNMENT_OPC**

**ASSIGNMENT_OPC** -> `=` **EXPRESSION**;  
-> `Ɛ`

**LOGIC_OR** -> **LOGIC_AND LOGIC_OR_2**

**LOGIC_OR_2** -> `or` **LOGIC_AND LOGIC_OR_2**
-> `Ɛ`

**LOGIC_AND** -> **EQUALITY LOGIC_AND_2**

**LOGIC_AND_2** -> `and` **EQUALITY LOGIC_AND_2**
-> `Ɛ`

**EQUALITY** -> **COMPARISON EQUALITY_2**

**EQUALITY_2** -> `!=` **COMPARISON EQUALITY_2**  
-> `==` **COMPARISON EQUALITY_2**  
-> `Ɛ`

**COMPARISON** -> **TERM COMPARISON_2**

**COMPARISON_2** -> `>` **TERM COMPARISON_2**  
-> `>=` **TERM COMPARISON_2**  
-> `<` **TERM COMPARISON_2**  
-> `<=` **TERM COMPARISON_2**  
-> `Ɛ`

**TERM** -> **FACTOR TERM_2**

**TERM_2** -> `-` **FACTOR TERM_2**  
-> `+` **FACTOR TERM_2**  
-> `Ɛ`

**FACTOR** -> **UNARY FACTOR_2**

**FACTOR_2** -> `/` **UNARY FACTOR_2**  
-> `*` **UNARY FACTOR_2**  
-> `Ɛ`

**UNARY** -> `!` **UNARY**  
-> `-` **UNARY**  
-> **CALL**

**CALL** -> **PRIMARY CALL_2**

**CALL_2** -> `(` **ARGUMENTS_OPC** `)` **CALL_2**  
-> `.` `id` **CALL_2**  
-> `Ɛ`

**CALL_OPC** -> **CALL** `.`  
-> `Ɛ`

**PRIMARY** -> `true`  
-> `false`  
-> `null`  
-> `this`  
-> `number`  
-> `string`  
-> `id`  
-> `(` **EXPRESSION** `)`  
-> `super` `.` `id`

---

#### Otras

**FUNCTION** -> `id` `(` **PARAMETERS_OPC** `)` **BLOCK**

**FUNCTIONS** -> **FUNCTION FUNCTIONS**  
-> `Ɛ`

**PARAMETERS_OPC** -> **PARAMETERS**  
-> `Ɛ`

**PARAMETERS** -> `id` **PARAMETERS_2**

**PARAMETERS_2** -> `,` `id` **PARAMETERS_2**  
-> `Ɛ`

**ARGUMENTS_OPC** -> **ARGUMENTS**  
-> `Ɛ`

**ARGUMENTS** -> **EXPRESSION ARGUMENTS_2**

**ARGUMENTS_2** -> `,` **EXPRESSION ARGUMENTS_2**  
-> `Ɛ`

### Conjunto Primero de la Gramatica usada

En la siguiente tabla se realiza el calculo del conjunto primero

| No terminal | Conjunto primero a calcular | Conjunto primero |
|-------------|-----------------------------|-----------------------------|
|**DECLARATION**| P(CLASS_DECL,FUN_DECL,VAR_DCL,STATEMENT, Ɛ) | `class`,`fun`,`var`, `for`, `if`, `print`, `return`, `while`, `{`, `this`, `super`, `false`, `true`, `null`, `number`, `string`, `(`, `identifier`, `!`, `-`, `Ɛ` |
|**CLASS_DECL**| ----- | `class` |
|**FUN_DECL**| ----- | `fun` |
|**VAR_DECL**| ------ | `var`|
|**STATEMENT**| P(EXPR_STMT,FOR_STMT,IF_STMT,PRINT_STMT,RETURN_STMT,WHILE_STMT,BLOCK) | `for`,`if`,`print`,`return`,`while`,`{`, `this`, `super`, `false`, `true`, `null`, `number`, `string`, `(`, `identifier`, `!`, `-` |
|**EXPR_STMT**| P(EXPRESSION) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-` |
|**FOR_STMT**| ------ | `for` |
|**IF_STMT**| ------ | `if` |
|**PRINT_STMT**| ------ | `print` |
|**RETURN_STMT**| ------ | `return` |
|**WHILE_STMT**| ------ | `while` |
|**BLOCK**| ------| `{` |
|**EXPRESSION**| P(ASSIGNMENT) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-` |
|**ASSIGNMENT**| P(LOGIC_OR) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-` |
|**LOGIC_OR**| P(LOGIC_AND) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-` |
|**LOGIC_AND**| P(EQUALITY) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-` |
|**EQUALITY**| P(COMPARISON) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-` |
|**COMPARISON**| P(TERM) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-` |
|**TERM**| P(FACTOR) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-` |
|**FACTOR**| P(UNARY) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-` |
|**UNARY**| P(CALL) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-` |
|**CALL**| P(PRIMARY) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier` |
|**PRIMARY**| ------ | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier` |
|**FUNCTION**| P(`id`) | `id` |
|**FUNCTIONS**| P(FUNCTION, Ɛ) | `id`, `Ɛ` |
|**PARAMETERS_OPC**| P(PARAMETERS, Ɛ) | `id`, `Ɛ` |
|**PARAMETERS**| P(`id`) | `id` |
|**PARAMETERS_2**| P(`,`, Ɛ) | `,`, `Ɛ` |
|**ARGUMENTS_OPC**| P(ARGUMENTS, Ɛ) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-`, `Ɛ` |
|**ARGUMENTS**| P(EXPRESSION) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-`|
|**ARGUMENTS_2**| P(`,`, Ɛ) | `,`, `Ɛ` |
|**CLASS_INHER**| P(`<`, Ɛ) | `<`, `Ɛ` |
|**VAR_INIT**| P(`=`, Ɛ) | `=`, `Ɛ` |
|**FOR_STMT_1**| P(VAR_DECL, EXPR_STMT, `;`) | `var`, `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `;` |
|**FOR_STMT_2**| P(EXPRESSION, `;`) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `;` |
|**FOR_STMT_3**| P(EXPRESSION, Ɛ) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `Ɛ` |
|**ELSE_STATEMENT**| P(`else`, Ɛ) | `else`, `Ɛ` |
|**RETURN_EXP_OPC**| P(EXPRESSION, Ɛ) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `Ɛ` |
|**BLOCK_DECL**| P(DECLARATION, Ɛ) | `class`, `fun`, `var`, `for`, `if`, `print`, `return`, `while`, `{`, `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `!`, `-`, `Ɛ` |
|**ASSIGNMENT_OPC**| P(`=`, Ɛ) | `=`, `Ɛ` |
|**LOGIC_OR_2**| P(`or`, Ɛ) | `or`, `Ɛ` |
|**LOGIC_AND_2**| P(`and`, Ɛ) | `and`, `Ɛ` |
|**EQUALITY_2**| P(`!=`, `==`, Ɛ) | `!=`, `==`, `Ɛ` |
|**COMPARISON_2**| P(`>`, `>=`, `<`, `<=`, Ɛ) | `>`, `>=`, `<`, `<=`, `Ɛ` |
|**TERM_2**| P(`-`, `+`, Ɛ) | `-`, `+`, `Ɛ` |
|**FACTOR_2**| P(`/`, `*`, Ɛ) | `/`, `*`, `Ɛ` |
|**CALL_2**| P(`(`, `.`, Ɛ) | `(`, `.`, `Ɛ` |
|**CALL_OPC**| P(CALL, Ɛ) | `this`,`super`,`false`,`true`,`null`,`number`,`string`,`(`, `identifier`, `Ɛ` |
