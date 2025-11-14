package aed;
import java.util.ArrayList;
import java.util.List;

import aed.GestorNotas.Nota;

public class Edr {
    private GestorExamenes gestorExamenes;
    private GestorNotas gestorNotas;
    private DetectorCopias detectorCopias;
    private CalculadorNotas calculadorNotas;
    private BuscadorVecinos buscadorVecinos;
    private GestorConteo gestorConteo;
    private int ladoAula;
    private int cantidadEstudiantes;

    public static Edr nuevoEdr(int ladoAula, int cantidadEstudiantes, List<Integer> examenCanonico) {
        return new Edr(ladoAula, cantidadEstudiantes, examenCanonico);
    }
    
    private Edr(int ladoAula, int cantidadEstudiantes, List<Integer> examenCanonico) {
        this.ladoAula = ladoAula;
        this.cantidadEstudiantes = cantidadEstudiantes;
        
        this.gestorExamenes = new GestorExamenes(cantidadEstudiantes, examenCanonico.size());
        this.gestorNotas = new GestorNotas(cantidadEstudiantes);
        this.detectorCopias = new DetectorCopias();
        this.calculadorNotas = new CalculadorNotas(examenCanonico);
        this.buscadorVecinos = new BuscadorVecinos(ladoAula, cantidadEstudiantes);
        this.gestorConteo = new GestorConteo(examenCanonico.size());
        
        for (int i = 0; i < cantidadEstudiantes; i++) {
            gestorNotas.registrarEstudiante(i);
        }
    }
    
    // OPERACIÓN 2: copiarse
    public void copiarse(int estudiante) {
        if (gestorNotas.estaEntregado(estudiante)) return;
        
        List<Integer> vecinos = buscadorVecinos.obtenerVecinos(estudiante);
        int mejorVecino = buscarMejorVecino(estudiante, vecinos);
        
        if (mejorVecino != -1) {
            copiarPrimerEjercicio(estudiante, mejorVecino);
        }
    }
    
    // OPERACIÓN 3: resolver
    public void resolver(int estudiante, int numeroEjercicio, int respuestaEjercicio) {
        if (gestorNotas.estaEntregado(estudiante)) return;
        
        int respuestaAnterior = gestorExamenes.obtenerRespuesta(estudiante, numeroEjercicio);
        
        // Actualizar conteo
        if (respuestaAnterior != -1) {
            gestorConteo.decrementarConteo(numeroEjercicio, respuestaAnterior);
        }
        gestorConteo.incrementarConteo(numeroEjercicio, respuestaEjercicio);
        
        // Actualizar examen
        gestorExamenes.establecerRespuesta(estudiante, numeroEjercicio, respuestaEjercicio);
        
        // Actualizar nota
        int nuevaNota = calculadorNotas.calcularNota(gestorExamenes.obtenerExamen(estudiante));
        gestorNotas.actualizarNota(estudiante, nuevaNota);
    }
    
    // OPERACIÓN 4: consultarDarkWeb
    public void consultarDarkWeb(int k, List<Integer> examenDW) {
        List<NodoHeap> peoresEstudiantes = gestorNotas.obtenerKPeores(k);
        
        for (NodoHeap nodo : peoresEstudiantes) {
            int estudiante = nodo.estudiante;
            
            // Limpiar respuestas anteriores del conteo
            for (int ej = 0; ej < gestorExamenes.obtenerCantidadEjercicios(); ej++) {
                if (gestorExamenes.tieneRespuesta(estudiante, ej)) {
                    int respuestaAnterior = gestorExamenes.obtenerRespuesta(estudiante, ej);
                    gestorConteo.decrementarConteo(ej, respuestaAnterior);
                }
            }
            
            // Actualizar examen con Dark Web
            gestorExamenes.reemplazarExamenCompleto(estudiante, examenDW);
            
            // Actualizar conteos nuevos
            for (int ej = 0; ej < gestorExamenes.obtenerCantidadEjercicios(); ej++) {
                int respuesta = examenDW.get(ej);
                gestorConteo.incrementarConteo(ej, respuesta);
            }
            
            // Actualizar nota y reinsertar
            int nuevaNota = calculadorNotas.calcularNota(gestorExamenes.obtenerExamen(estudiante));
            gestorNotas.reinsertarEstudiante(new NodoHeap(estudiante, nuevaNota));
        }
    }
    
    // OPERACIÓN 5: notas
    public double[] notas() {
        return gestorNotas.obtenerTodasNotas();
    }
    
    // OPERACIÓN 6: entregar
    public void entregar(int estudiante) {
        gestorNotas.marcarEntregado(estudiante);
    }
    
    // OPERACIÓN 7: chequearCopias
    public List<Integer> chequearCopias() {
        List<Integer> sospechosos = detectorCopias.detectarCopias(
            gestorExamenes, gestorConteo, gestorNotas, cantidadEstudiantes);
        
        // Eliminar sospechosos del sistema de notas
        for (int sospechoso : sospechosos) {
            gestorNotas.eliminarEstudiante(sospechoso);
        }
        
        return sospechosos;
    }
    
    // OPERACIÓN 8: corregir
    public NotaFinal[] corregir() {
        return gestorNotas.obtenerEstudiantesEntregados();
    }
    
    // MÉTODOS AUXILIARES PRIVADOS
    private int buscarMejorVecino(int estudiante, List<Integer> vecinos) {
        int mejorVecino = -1;
        int maxEjerciciosNuevos = -1;
        
        for (int vecino : vecinos) {
            if (gestorNotas.estaEntregado(vecino)) continue;
            
            int ejerciciosNuevos = contarEjerciciosNuevos(estudiante, vecino);
            if (ejerciciosNuevos > maxEjerciciosNuevos) {
                maxEjerciciosNuevos = ejerciciosNuevos;
                mejorVecino = vecino;
            }
        }
        return mejorVecino;
    }
    
    private int contarEjerciciosNuevos(int estudiante, int vecino) {
        int contador = 0;
        int cantidadEjercicios = gestorExamenes.obtenerCantidadEjercicios();
        
        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            if (!gestorExamenes.tieneRespuesta(estudiante, ej) && 
                gestorExamenes.tieneRespuesta(vecino, ej)) {
                contador++;
            }
        }
        return contador;
    }
    
    private void copiarPrimerEjercicio(int estudiante, int vecino) {
        int cantidadEjercicios = gestorExamenes.obtenerCantidadEjercicios();
        
        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            if (!gestorExamenes.tieneRespuesta(estudiante, ej) && 
                gestorExamenes.tieneRespuesta(vecino, ej)) {
                
                int respuesta = gestorExamenes.obtenerRespuesta(vecino, ej);
                resolver(estudiante, ej, respuesta);
                break;
            }
        }
    }
}