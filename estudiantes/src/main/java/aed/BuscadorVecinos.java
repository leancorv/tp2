package aed;

import java.util.ArrayList;
import java.util.List;

public class BuscadorVecinos {
    private int cantidadEstudiantes;
    private int cantidadEstudiantesxFila;
    
    public BuscadorVecinos(int ladoAula, int cantidadEstudiantes){
        this.cantidadEstudiantes = cantidadEstudiantes;
        this.cantidadEstudiantesxFila = (ladoAula + 1)/2;
        /*[0] - [1] - [2] -
          [3] - [4] - [5] -  En una ladoAula = 7, entran 3 por fila, 
          [6] - [7] - [8] -
         */
    }

    public List<Integer> obtenerVecinos(int estudianteId){
        List<Integer> vecinos = new ArrayList<>();

        // Agrego el vecino de arriba
        if((estudianteId - cantidadEstudiantesxFila) >= 0){
            int vecino = estudianteId - cantidadEstudiantes;
            vecinos.add(vecino);
        }

        //Agrego el vecino de la izquierda si no esta contra la pared derecha
        if((estudianteId%cantidadEstudiantes) != 0){
            vecinos.add(estudianteId-1);
        }

        //Agrego el vecino de la derecha si no esta contra la pared izquierda
        int alumnoALaDerecha = cantidadEstudiantes - 1;
        if(estudianteId%cantidadEstudiantes != alumnoALaDerecha){
            vecinos.add(estudianteId+1);
        }

        return vecinos;
    }
}
