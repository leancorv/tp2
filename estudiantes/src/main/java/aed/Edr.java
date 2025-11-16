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
    private boolean[] historialDeCopiones;
    private int[] contadorDeCopiados;
    private int[] contadorDeCopiones;

    public static Edr nuevoEdr(int ladoAula, int cantidadEstudiantes, int[] examenCanonico) {
        return new Edr(ladoAula, cantidadEstudiantes, examenCanonico);
    }
    
    public Edr(int ladoAula, int cantidadEstudiantes, int[] examenCanonico) {
        this.ladoAula = ladoAula;
        this.cantidadEstudiantes = cantidadEstudiantes;
        
        this.gestorExamenes = new GestorExamenes(cantidadEstudiantes, examenCanonico.length);
        this.gestorNotas = new GestorNotas(cantidadEstudiantes);
        this.detectorCopias = new DetectorCopias();
        this.calculadorNotas = new CalculadorNotas(examenCanonico);
        this.buscadorVecinos = new BuscadorVecinos(ladoAula, cantidadEstudiantes);
        this.gestorConteo = new GestorConteo(examenCanonico.length);
        
        for (int i = 0; i < cantidadEstudiantes; i++) {
            gestorNotas.registrarEstudiante(i);
        }
        // Inicializar trackers de copias
        this.historialDeCopiones = new boolean[cantidadEstudiantes];
        this.contadorDeCopiados = new int[cantidadEstudiantes];
        this.contadorDeCopiones = new int[cantidadEstudiantes];
        for (int i = 0; i < cantidadEstudiantes; i++) this.historialDeCopiones[i] = false;
        for (int i = 0; i < cantidadEstudiantes; i++) this.contadorDeCopiados[i] = 0;
        for (int i = 0; i < cantidadEstudiantes; i++) this.contadorDeCopiones[i] = 0;
    }
    
    // OPERACIÓN 2: copiarse
    public void copiarse(int estudiante) {
        if (gestorNotas.estaEntregado(estudiante)) return;
        
        List<Integer> vecinos = buscadorVecinos.obtenerVecinos(estudiante);
        int mejorVecino = buscarMejorVecino(estudiante, vecinos);
        
        if (mejorVecino != -1) {
            copiarPrimerEjercicio(estudiante, mejorVecino);
            // Registrar que este estudiante copió y que el vecino fue usado como fuente
            historialDeCopiones[estudiante] = true;
            contadorDeCopiones[estudiante]++;
            contadorDeCopiados[mejorVecino]++;
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
        int nuevaNota = calculadorNotas.calcularNota(gestorExamenes, estudiante);
        gestorNotas.actualizarNota(estudiante, nuevaNota);
    }
    
    // OPERACIÓN 4: consultarDarkWeb
    public void consultarDarkWeb(int k, int[] examenDW) {
        List<Nota> peoresEstudiantes = gestorNotas.obtenerKPeores(k);
        
        for (Nota nodo : peoresEstudiantes) {
            int estudiante = nodo.getEstudianteId();
            
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
                int respuesta = examenDW[ej];
                gestorConteo.incrementarConteo(ej, respuesta);
            }
            
            // Actualizar nota y reinsertar
            int nuevaNota = calculadorNotas.calcularNota(gestorExamenes, estudiante);
            gestorNotas.actualizarNota(estudiante, nuevaNota);
            // gestorNotas.actualizarHandles();
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
    public int[] chequearCopias() {
        int[] sospechosos = detectorCopias.detectarCopias(
            gestorExamenes, gestorConteo, gestorNotas, cantidadEstudiantes);
        // Filtrar sospechosos: excluir a quienes fueron usados más como fuente que quienes copiaron
        List<Integer> lista = new ArrayList<>();
        for (int s : sospechosos) lista.add(s);
        List<Integer> listaFinal = new ArrayList<>();
        for (Integer idx : lista) {
            if (contadorDeCopiados[idx] > contadorDeCopiones[idx]) continue;
            listaFinal.add(idx);
        }
        sospechosos = new int[listaFinal.size()];
        for (int i = 0; i < listaFinal.size(); i++) sospechosos[i] = listaFinal.get(i);

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
            // Ignorar vecinos inválidos (defensivo) y los que ya entregaron
            if (vecino < 0 || vecino >= cantidadEstudiantes) continue;
            if (gestorNotas.estaEntregado(vecino)) continue;

            int ejerciciosNuevos = contarEjerciciosNuevos(estudiante, vecino);
            int correctNuevos = contarEjerciciosNuevosCorrectos(estudiante, vecino);
            int bestCorrect = (mejorVecino == -1 ? 0 : contarEjerciciosNuevosCorrectos(estudiante, mejorVecino));

            // Preferir vecino que aporte más respuestas nuevas CORRECTAS
            if (correctNuevos > bestCorrect) {
                maxEjerciciosNuevos = ejerciciosNuevos;
                mejorVecino = vecino;
            } else if (correctNuevos == bestCorrect) {
                // Si hay empate en correctos, preferir más ejercicios nuevos
                if (ejerciciosNuevos > maxEjerciciosNuevos ||
                    // En caso de nuevo empate: preferir vecino con índice mayor (derecha/abajo)
                    (ejerciciosNuevos == maxEjerciciosNuevos && (mejorVecino == -1 || vecino > mejorVecino))) {
                    maxEjerciciosNuevos = ejerciciosNuevos;
                    mejorVecino = vecino;
                }
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

    private int contarEjerciciosNuevosCorrectos(int estudiante, int vecino) {
        int contador = 0;
        int cantidadEjercicios = gestorExamenes.obtenerCantidadEjercicios();

        for (int ej = 0; ej < cantidadEjercicios; ej++) {
            if (!gestorExamenes.tieneRespuesta(estudiante, ej) && 
                gestorExamenes.tieneRespuesta(vecino, ej)) {
                int respuesta = gestorExamenes.obtenerRespuesta(vecino, ej);
                if (calculadorNotas.esRespuestaCorrecta(ej, respuesta)) {
                    contador++;
                }
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