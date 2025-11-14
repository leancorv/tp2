package aed;
import java.util.ArrayList;
import java.util.List;

public class BuscadorVecinos {
    private int ladoAula;
    private int cantidadEstudiantes;
    
    public BuscadorVecinos(int ladoAula, int cantidadEstudiantes) {
        this.ladoAula = ladoAula;
        this.cantidadEstudiantes = cantidadEstudiantes;
    }
    
    public List<Integer> obtenerVecinos(int estudiante) {
        List<Integer> vecinos = new ArrayList<>();
        
        int fila = estudiante / ladoAula;
        int columna = estudiante % ladoAula;
        
        if (columna > 0) vecinos.add(estudiante - 1);
        if (columna < ladoAula - 1) vecinos.add(estudiante + 1);
        if (fila < (cantidadEstudiantes / ladoAula) - 1) vecinos.add(estudiante + ladoAula);
        
        return vecinos;
    }
}
