package aed;
import java.util.ArrayList;
import java.util.List;

public class GestorExamenes {
    private int[][] examenes;
    private int cantidadEstudiantes;
    private int cantidadEjercicios;

    public GestorExamenes(int cantidadEstudiantes, int cantidadEjercicios) {
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
    
    
    // OPERACIONES DE CONSULTA
    public int obtenerCantidadEjercicios() {
        return cantidadEjercicios;
    }
    
    public int obtenerCantidadEstudiantes() {
        return cantidadEstudiantes;
    }
    
}
