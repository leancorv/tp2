package aed;
import java.util.ArrayList;
import java.util.List;

public class DetectorCopias {
    
    public int[] detectarCopias(GestorExamenes gestorExamenes, 
                                      GestorConteo gestorConteo,
                                      GestorNotas gestorNotas,
                                      int cantidadEstudiantes) {
        
        int[] sospechosos = new int[0];
        int cantidadEjercicios = gestorExamenes.obtenerCantidadEjercicios();
        
        for (int est = 0; est < cantidadEstudiantes; est++) {
            if (!gestorNotas.estaEntregado(est)) continue;
            
            if (esSospechoso(est, gestorExamenes, gestorConteo, cantidadEstudiantes, cantidadEjercicios)) {
                // No se puede usar add en un array, se debería usar una estructura dinámica o manejar el array de otra forma
                int[] nuevoArray = new int[sospechosos.length + 1];
                nuevoArray[sospechosos.length] = est;
                sospechosos = nuevoArray;
            }
        }
        
        return sospechosos;
    }
    
    private boolean esSospechoso(int estudiante, GestorExamenes gestorExamenes,
                               GestorConteo gestorConteo, int cantidadEstudiantes, 
                               int cantidadEjercicios) {
        
        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            if (!gestorExamenes.tieneRespuesta(estudiante, ej)) continue;
            
            int respuesta = gestorExamenes.obtenerRespuesta(estudiante, ej);
            int totalConMismaRespuesta = gestorConteo.obtenerConteo(ej, respuesta);
            double porcentaje = (double) totalConMismaRespuesta / cantidadEstudiantes;
            
            if (porcentaje < 0.25) {
                return false;
            }
        }
        return true;
    }
}