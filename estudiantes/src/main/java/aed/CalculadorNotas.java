package aed;

//Nos creamos el constructor y lo que hacemos en el fondo siempre son comparaciones por acceso directo a arrays y sumando y restando 1,
//por lo que calcular la nota siempre es O(R)
public class CalculadorNotas {
    private int[] examenCanonico;
    private int cantidadEjercicios;

    public CalculadorNotas(int[] examenCanonico2) {
        this.examenCanonico = examenCanonico2;
        this.cantidadEjercicios = examenCanonico2.length;
    }

    public int calcularNota(GestorExamenes gestorExamenes, int estudiante) {
        int ejerciciosCorrectos = 0;

        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            if (gestorExamenes.obtenerExamen(estudiante)[ej] != -1 && gestorExamenes.obtenerExamen(estudiante)[ej] == examenCanonico[ej]) {
                ejerciciosCorrectos++;
            }
        }
        if (cantidadEjercicios == 0) {
            return 0;
        }
        int porcentaje = (ejerciciosCorrectos * 100) / cantidadEjercicios;
        return porcentaje;
    }

    // Indica si una respuesta dada para un ejercicio es la correcta
    public boolean esRespuestaCorrecta(int ejercicio, int respuesta) {
        if (ejercicio < 0 || ejercicio >= cantidadEjercicios) return false;
        return examenCanonico[ejercicio] == respuesta;
    }

}
