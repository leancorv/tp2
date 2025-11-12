package aed;
import java.util.ArrayList;
import java.util.List;

public class GestorDeExamenes {
    private int[][] examenes;
    private int cantidadEstudiantes;
    private int cantidadEjercicios;

    public GestorDeExamenes(int cantidadEstudiantes, int cantidadEjercicios) {
        this.cantidadEstudiantes = cantidadEstudiantes;
        this.cantidadEjercicios = cantidadEjercicios;
        this.examenes = new int[cantidadEstudiantes][cantidadEjercicios];
        
        for (int i = 0; i < cantidadEstudiantes; i++) {
            for (int j = 0; j < cantidadEjercicios; j++) {
                examenes[i][j] = -1;
            }
        }
    }
    
    public void establecerRespuesta(int estudiante, int ejercicio, int respuesta) {
        examenes[estudiante][ejercicio] = respuesta;
    }
    
    public int obtenerRespuesta(int estudiante, int ejercicio) {
        return examenes[estudiante][ejercicio];
    }
    
    public boolean tieneRespuesta(int estudiante, int ejercicio) {
        return obtenerRespuesta(estudiante, ejercicio) != -1;
    }
    
    public int[] obtenerExamen(int estudiante) {
        return examenes[estudiante];
    }
    
    // OPERACIONES PARA DARK WEB
    public void reemplazarExamenCompleto(int estudiante, List<Integer> nuevoExamen) {
        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            examenes[estudiante][ej] = nuevoExamen.get(ej);
        }
    }
    
    public void limpiarExamen(int estudiante) {
        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            examenes[estudiante][ej] = -1;
        }
    }
    
    // OPERACIONES DE CONSULTA
    public int obtenerCantidadEjercicios() {
        return cantidadEjercicios;
    }
    
    public int obtenerCantidadEstudiantes() {
        return cantidadEstudiantes;
    }
    
    public List<Integer> obtenerRespuestasPorEjercicio(int ejercicio) {
        List<Integer> respuestas = new ArrayList<>();
        for (int est = 0; est < cantidadEstudiantes; est++) {
            respuestas.add(examenes[est][ejercicio]);
        }
        return respuestas;
    }
    
    public boolean estaCompleto(int estudiante) {
        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            if (examenes[estudiante][ej] == -1) {
                return false;
            }
        }
        return true;
    }
    
    public int contarEjerciciosResueltos(int estudiante) {
        int contador = 0;
        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            if (examenes[estudiante][ej] != -1) {
                contador++;
            }
        }
        return contador;
    }
    
}