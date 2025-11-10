package aed;

public class Estudiante /*implements Comparable<Estudiante>*/{
    /*Falta implementar la clase de comparacion
     * X lo q entendi creo q puede funcionar esto pero lo hice con sueño xd,
     * Esta muy por arriba igual faltaria mas cosas si no me equivoco
     */

    private int id;
    private boolean entrego;
    private boolean sospechoso;
    private int nota;
    /*private int[] respuestas ?*/

    public Estudiante(int id){
        this.id = id;
        this.entrego = false;
        this.sospechoso = false;
        this.nota = 0;
    }

    public int obtenerId(){
        return id;
    }

    public boolean examenEntregado(){
        return entrego;
    }

    public boolean esSospechoso(){
        return sospechoso;
    }

    public int obtenerNota(){
        return nota;
    }

    /*Aca va el metodo de comparacion q no me acuerdo xd */

}


