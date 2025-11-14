package aed;
import java.util.ArrayList;
import java.util.List;

public class BuscadorVecinos {
    private int cantidadEstudiantes;
    private int cantidadEstudiantesxFila; 
    /*{0}-{1}-{2}
      {3}-{4}-{5}
      {6}-{7}-{8}
      {9}-{10}-{11}
      {12}-{13}- -
    */ 
    
    public BuscadorVecinos(int ladoAula, int cantidadEstudiantes) {
        this.cantidadEstudiantes = cantidadEstudiantes; 
        this.cantidadEstudiantesxFila = (ladoAula + 1)/2;
    }
    
    public List<Integer> obtenerVecinos(int estudianteId) {
        List<Integer> vecinos = new ArrayList<>();
        
        //Agrego el vecino de arriba
        if((estudianteId - cantidadEstudiantesxFila) >= 0){
            int vecino = estudianteId - cantidadEstudiantes;
            vecinos.add(vecino);
        }

        //Agrego el vecino de la izquierda si no esta contra la pared izquierda
        // el mod(cantEstudiantesxFila) de los asientos a la izquierda siempre es = 0
        if((estudianteId % cantidadEstudiantesxFila) != 0){
            vecinos.add(estudianteId - 1);
        }

        //Agrego el vecino de la derecha si no esta contra la pared derecha
        int alumnoALaDerecha = cantidadEstudiantesxFila - 1; //saco el resto mod(cantEstudiantesxFila) de los asientos de la derecha
        if((estudianteId % cantidadEstudiantesxFila) != alumnoALaDerecha && estudianteId!=cantidadEstudiantes){
            vecinos.add(estudianteId+1);
        }

        return vecinos;
    }
}
