# Compiladores

ESCOM IPN Compiladores con el profesdor Gabriel de Jésus

## Como usar

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
