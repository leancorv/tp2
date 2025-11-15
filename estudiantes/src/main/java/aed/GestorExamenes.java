package aed;
import java.util.ArrayList;
import java.util.List;

public class GestorExamenes {
    private int[][] examenes;
    private int cantidadEstudiantes;
    private int cantidadEjercicios;

    //El constructor donde inicializamos un arreglo de EXR elementos con respuestas en blanco, -1
    //Complejidad es O(EXR)
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

    //Todas estas operaciones son O(1) por ser acceso a arrays y cambiar o consultar valores en ellos
    
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
    //Notar que aca si o si tenes que recorrer todo el examen de cada uno de los k copiones y copiarlo asi que es O(kR)
    public void reemplazarExamenCompleto(int estudiante, int[] nuevoExamen) {
        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            examenes[estudiante][ej] = nuevoExamen[ej];
        }
    }
    
    
    // OPERACIONES DE CONSULTA O(1)
    public int obtenerCantidadEjercicios() {
        return cantidadEjercicios;
    }
    
    public int obtenerCantidadEstudiantes() {
        return cantidadEstudiantes;
    }
    
}
