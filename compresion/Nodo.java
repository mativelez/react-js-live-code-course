package compresion;

public class Nodo {
    Character caracter;
    Integer frecuencia;
    Nodo izq;
    Nodo der;

    public Nodo(Character caracter, Integer frecuencia) {
        this.caracter = caracter;
        this.frecuencia = frecuencia;
    }

    public Nodo(Character caracter, Integer frecuencia, Nodo izq, Nodo der) {
        this.caracter = caracter;
        this.frecuencia = frecuencia;
        this.izq = izq;
        this.der = der;
    }

    public boolean esHoja() {
        return izq == null && der == null;
    }

    @Override
    public String toString() {
        if (esHoja()) {
            return "['" + caracter + "' : " + frecuencia + "]";
        }
        return "[∑" + frecuencia + "]";
    }
}
