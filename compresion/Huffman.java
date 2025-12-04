package compresion;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Huffman {

    public static void codifica(Nodo raiz, String str, Map<Character, String> codigoHuffman) {
        if (raiz == null) {
            return;
        }

        if (raiz.esHoja()) {
            // En el caso de un único símbolo, se usa "1" para evitar la cadena vacía
            codigoHuffman.put(raiz.caracter, str.length() > 0 ? str : "1");
            return;
        }

        codifica(raiz.izq, str + '0', codigoHuffman);
        codifica(raiz.der, str + '1', codigoHuffman);
    }

    public static int decodifica(Nodo raiz, int index, StringBuilder sb) {
        return decodifica(raiz, index, sb, null, true);
    }

    public static Nodo construirArbolHuffman(String texto) {
        if (texto == null || texto.isEmpty()) {
            System.out.println("No se puede construir el árbol de Huffman para un texto vacío.");
            return null;
        }

        Map<Character, Integer> frecuencias = calcularFrecuencias(texto);
        System.out.println("Frecuencias calculadas: " + frecuencias);

        PriorityQueue<Nodo> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.frecuencia, b.frecuencia));
        frecuencias.forEach((caracter, frecuencia) -> pq.add(new Nodo(caracter, frecuencia)));

        while (pq.size() > 1) {
            Nodo izq = pq.poll();
            Nodo der = pq.poll();

            Nodo nuevo = new Nodo(null, izq.frecuencia + der.frecuencia, izq, der);
            pq.add(nuevo);
        }

        Nodo raiz = pq.peek();

        Map<Character, String> codigoHuffman = new HashMap<>();
        codifica(raiz, "", codigoHuffman);

        imprimirCodigosOrdenados(codigoHuffman);

        StringBuilder sb = new StringBuilder();
        for (char c : texto.toCharArray()) {
            sb.append(codigoHuffman.get(c));
        }

        System.out.println("Texto original: " + texto);
        System.out.println("Texto codificado: " + sb);

        System.out.print("Texto decodificado paso a paso: ");
        String decodificado = decodificarCadena(raiz, sb.toString());
        System.out.println();
        System.out.println("Texto decodificado: " + decodificado);

        return raiz;
    }

    public static String decodificarCadena(Nodo raiz, String codificado) {
        if (codificado == null || codificado.isEmpty()) {
            return "";
        }

        StringBuilder resultado = new StringBuilder();
        StringBuilder bits = new StringBuilder(codificado);
        int indice = -1;

        while (indice < bits.length() - 1) {
            indice = decodifica(raiz, indice, bits, resultado, true);
        }

        return resultado.toString();
    }

    private static int decodifica(Nodo raiz, int index, StringBuilder sb, StringBuilder resultado, boolean imprimir) {
        if (raiz == null) {
            return index;
        }

        if (raiz.esHoja()) {
            if (imprimir) {
                System.out.print(raiz.caracter);
            }
            if (resultado != null) {
                resultado.append(raiz.caracter);
            }
            return index;
        }

        index++;
        if (index >= sb.length()) {
            return index;
        }

        char bit = sb.charAt(index);
        if (bit == '0') {
            index = decodifica(raiz.izq, index, sb, resultado, imprimir);
        } else {
            index = decodifica(raiz.der, index, sb, resultado, imprimir);
        }

        return index;
    }

    private static Map<Character, Integer> calcularFrecuencias(String texto) {
        Map<Character, Integer> frecuencias = new HashMap<>();
        for (char c : texto.toCharArray()) {
            frecuencias.merge(c, 1, Integer::sum);
        }
        return frecuencias;
    }

    private static void imprimirCodigosOrdenados(Map<Character, String> codigoHuffman) {
        System.out.println("Códigos Huffman generados (ordenados para leer más fácil):");
        new TreeMap<>(codigoHuffman).forEach((caracter, codigo) ->
                System.out.println(" • '" + caracter + "' -> " + codigo));
    }
}
