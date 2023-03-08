package mx.ipn.escom.ScannerComponents;


public class CharArrayManager {
    private final char[] array;
    private int index;
    public CharArrayManager(char[] array){
        this.array = array;
        index = 0;
    }

    public char getNextChar(){
        return array[index++];
    }
    public void backPosition(){
        --index;
    }
    public void show_characters(){
        System.out.println(array.length);
        for (int i=0;i<array.length;i++)
            System.out.println(array[i] + " posicion:" + i+(array[i]=='\n'?" salto de linea \\n":"")+(array[i]=='\r'?" retorno de carro \\r":"")+(array[i]=='\t'?" tabulador \\t":""));
    }
    public boolean hasNext(){
        return index<array.length;
    }
}
