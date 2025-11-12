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
        validarEjercicio(ejercicio);
        for (int i = 0; i < 10; i++) {
            conteoRespuestas[ejercicio][i] = 0;
        }
    }
    
    public void limpiarTodosLosConteos() {
        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            limpiarConteoEjercicio(ej);
        }
    }
    
    // OPERACIONES DE CONSULTA
    public int obtenerRespuestaMasPopular(int ejercicio) {
        validarEjercicio(ejercicio);
        int maxConteo = -1;
        int respuestaMasPopular = -1;
        
        for (int resp = 0; resp < 10; resp++) {
            if (conteoRespuestas[ejercicio][resp] > maxConteo) {
                maxConteo = conteoRespuestas[ejercicio][resp];
                respuestaMasPopular = resp;
            }
        }
        return respuestaMasPopular;
    }
    
    public int obtenerTotalRespuestasEjercicio(int ejercicio) {
        int total = 0;
        for (int resp = 0; resp < 10; resp++) {
            total += conteoRespuestas[ejercicio][resp];
        }
        return total;
    }
    
    public double obtenerPorcentajeRespuesta(int ejercicio, int respuesta, int totalEstudiantes) {
        if (totalEstudiantes == 0) return 0.0;
        return (double) conteoRespuestas[ejercicio][respuesta] / totalEstudiantes;
    }
    
}