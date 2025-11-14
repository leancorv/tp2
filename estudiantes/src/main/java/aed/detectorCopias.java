import java.util.ArrayList;
import java.util.List;
package aed;

public class DetectorCopias {
    
    public List<Integer> detectarCopias(GestorExamenes gestorExamenes, 
                                      GestorConteo gestorConteo,
                                      GestorNotas gestorNotas,
                                      int cantidadEstudiantes) {
        
        List<Integer> sospechosos = new ArrayList<>();
        int cantidadEjercicios = gestorExamenes.obtenerCantidadEjercicios();
        
        for (int est = 0; est < cantidadEstudiantes; est++) {
            if (!gestorNotas.estaEntregado(est)) continue;
            
            if (esSospechoso(est, gestorExamenes, gestorConteo, cantidadEstudiantes, cantidadEjercicios)) {
                sospechosos.add(est);
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