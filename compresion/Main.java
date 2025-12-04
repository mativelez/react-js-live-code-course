package compresion;

public class Main {
    public static void main(String[] args) {
        String texto = "WWXWYZWX";
        Huffman.construirArbolHuffman(texto);

        System.out.println("\n\nPrueba con otra cadena:");
        Huffman.construirArbolHuffman("AABSCIIFFVVCEESF");
    }
}
