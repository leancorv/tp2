package aed;
import java.util.ArrayList;
import java.util.List;

public class DetectorCopias {
    
    public int[] detectarCopias(GestorExamenes gestorExamenes, 
                                      GestorConteo gestorConteo,
                                      GestorNotas gestorNotas,
                                      int cantidadEstudiantes) {
        
        boolean[] sospechosos = new boolean[cantidadEstudiantes];
        int cantidadEjercicios = gestorExamenes.obtenerCantidadEjercicios();
        // queremos devolver un array con la posición de los estudiantes sospechosos
        for (int est = 0; est < cantidadEstudiantes; est++) {
            if (!gestorNotas.estaEntregado(est)) continue;
            
            if (esSospechoso(est, gestorExamenes, gestorConteo, cantidadEstudiantes, cantidadEjercicios)) {
                sospechosos[est] = true;
            }
        }
        // Construir el array de sospechosos
        List<Integer> listaSospechosos = new ArrayList<>();
        for (int i = 0; i < cantidadEstudiantes; i++) {
            if (sospechosos[i]) {
                listaSospechosos.add(i);
            }
        }
        // Convertir la lista a un array de enteros
        int[] resultado = new int[listaSospechosos.size()];
        for (int i = 0; i < listaSospechosos.size(); i++) {
            resultado[i] = listaSospechosos.get(i);
        }
        return resultado;
    }
    
    private boolean esSospechoso(int estudiante, GestorExamenes gestorExamenes,
                               GestorConteo gestorConteo, int cantidadEstudiantes, 
                               int cantidadEjercicios) {
        // Si no hay otros estudiantes contra los que comparar, no es sospechoso
        if (cantidadEstudiantes <= 1) return false;

        int otrosEstudiantes = cantidadEstudiantes - 1;

        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            if (!gestorExamenes.tieneRespuesta(estudiante, ej)) continue;

            int respuesta = gestorExamenes.obtenerRespuesta(estudiante, ej);
            int totalConMismaRespuesta = gestorConteo.obtenerConteo(ej, respuesta);

            // Ajustar si el contador incluye al propio estudiante
            int otrosConMismaRespuesta = Math.max(0, totalConMismaRespuesta - 1);

            double porcentaje = (double) otrosConMismaRespuesta / (double) otrosEstudiantes;

            // Si alguna respuesta del estudiante NO alcanza el umbral, no es sospechoso
            if (porcentaje <= 0.25) {
                return false;
            }
        }

        // Todas las respuestas alcanzaron el umbral => sospechoso
        return true;
    }
}