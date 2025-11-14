package aed;

public class GestorConteo {
    private int[][] conteoRespuestas;
    
    public GestorConteo(int cantidadEjercicios) {
        this.conteoRespuestas = new int[cantidadEjercicios][10]; // 10 posibles respuestas
    }
    
    public void incrementarConteo(int ejercicio, int respuesta) {
        conteoRespuestas[ejercicio][respuesta]++;
    }
    
    public void decrementarConteo(int ejercicio, int respuesta) {
        if (conteoRespuestas[ejercicio][respuesta] > 0) {
            conteoRespuestas[ejercicio][respuesta]--;
        }
    }
    
    public int obtenerConteo(int ejercicio, int respuesta) {
        return conteoRespuestas[ejercicio][respuesta];
    }
    
    
}
