package aed;

public class GestorConteo {
    private int[][] conteoRespuestas;
    private int cantidadEjercicios;
    
    public GestorConteo(int cantidadEjercicios) {
        this.cantidadEjercicios = cantidadEjercicios;
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
    
    
    public void limpiarConteoEjercicio(int ejercicio) {
        for (int i = 0; i < 10; i++) {
            conteoRespuestas[ejercicio][i] = 0;
        }
    }
    
    public void limpiarTodosLosConteos() {
        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            limpiarConteoEjercicio(ej);
        }
    }
    
}
