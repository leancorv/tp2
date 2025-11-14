package aed;


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
            if (gestorExamenes.obtenerExamen(estudiante)[ej] != -1 && gestorExamenes.obtenerExamen(estudiante)[ej] == examenCanonico.length) {
                ejerciciosCorrectos++;
            }
        }
        if (cantidadEjercicios == 0) {
            return 0;
        }
        int porcentaje = (ejerciciosCorrectos * 100) / cantidadEjercicios;
        return porcentaje;
    }

}
