package aed;
import java.util.ArrayList;
import java.util.List;

//Nos creamos el constructor y notar que todas estas operaciones estan acotadas porque solo hay 3 posibles vecinos como max
//Asi que en el fondo todas las operaciones terminan siendo O(1)
public class BuscadorVecinos {
    private int cantidadEstudiantes;
    private int cantidadEstudiantesxFila; 
    
    public BuscadorVecinos(int ladoAula, int cantidadEstudiantes) {
        this.cantidadEstudiantes = cantidadEstudiantes; 
        this.cantidadEstudiantesxFila = (ladoAula + 1)/2;
    }
    
    public List<Integer> obtenerVecinos(int estudianteId) {
        List<Integer> vecinos = new ArrayList<>();
        
        // Vecino de arriba
        int vecinoArriba = estudianteId - cantidadEstudiantesxFila;
        if (vecinoArriba >= 0 && vecinoArriba < cantidadEstudiantes) {
            vecinos.add(vecinoArriba);
        }

        // Vecino de la izquierda
        if (estudianteId % cantidadEstudiantesxFila != 0) {
            int vecinoIzquierda = estudianteId - 1;
            if (vecinoIzquierda >= 0 && vecinoIzquierda < cantidadEstudiantes) {
                vecinos.add(vecinoIzquierda);
            }
        }

        // Vecino de la derecha
        if ((estudianteId + 1) % cantidadEstudiantesxFila != 0) {
            int vecinoDerecha = estudianteId + 1;
            if (vecinoDerecha < cantidadEstudiantes) {
                vecinos.add(vecinoDerecha);
            }
        }

        // Vecino de abajo
        int vecinoAbajo = estudianteId + cantidadEstudiantesxFila;
        if (vecinoAbajo < cantidadEstudiantes) {
            vecinos.add(vecinoAbajo);
        }

        return vecinos;
    }
}
