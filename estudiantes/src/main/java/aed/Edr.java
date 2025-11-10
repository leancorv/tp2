package aed;
import java.util.ArrayList;

public class Edr {

    private int cant_estudiantes;
    private int cant_ejercicios;
    private int tamaño_aula;
    private int[] examen_canonico;
    /* 
    private Heap<Estudiante> heap; Aca no se como se implementa el heap,
                                     pero creo q esto era lo q habiamos hablado*/

    /*Faltaria ver si vamos a usar los handles, y las demas estructuras no las termine de
     * enteder o si la vamos a usar, creo q la lista de entregados no lo ibamos a usar al final
     */

    public Edr(int LadoAula, int Cant_estudiantes, int[] ExamenCanonico) {
        throw new UnsupportedOperationException("Sin implementar");
    }

//-------------------------------------------------NOTAS--------------------------------------------------------------------------

    public double[] notas(){
        throw new UnsupportedOperationException("Sin implementar");
    }

//------------------------------------------------COPIARSE------------------------------------------------------------------------



    public void copiarse(int estudiante) {
        throw new UnsupportedOperationException("Sin implementar");
    }


//-----------------------------------------------RESOLVER----------------------------------------------------------------




    public void resolver(int estudiante, int NroEjercicio, int res) {
        throw new UnsupportedOperationException("Sin implementar");
    }



//------------------------------------------------CONSULTAR DARK WEB-------------------------------------------------------

    public void consultarDarkWeb(int n, int[] examenDW) {
        throw new UnsupportedOperationException("Sin implementar");
    }
 

//-------------------------------------------------ENTREGAR-------------------------------------------------------------

    public void entregar(int estudiante) {
        throw new UnsupportedOperationException("Sin implementar");
    }

//-----------------------------------------------------CORREGIR---------------------------------------------------------

    public NotaFinal[] corregir() {
        throw new UnsupportedOperationException("Sin implementar");
    }

//-------------------------------------------------------CHEQUEAR COPIAS-------------------------------------------------

    public int[] chequearCopias() {
        throw new UnsupportedOperationException("Sin implementar");
    }
}
