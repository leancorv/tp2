package aed;

public class CalculadorNotas {
    private List<Integer> examenCanonico;
    private int cantidadEjercicios;

    public CalculadorNotas(List<Integer> examenCanonico) {
        this.examenCanonico = examenCanonico;
        this.cantidadEjercicios = examenCanonico.size();
    }

    public int calcularNota(GestorExamenes gestorExamenes, int estudiante) {
        int ejerciciosCorrectos = 0;

        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            if (examenEstudiante[ej] != -1 && examenEstudiante[ej] == examenCanonico.get(ej)) {
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
