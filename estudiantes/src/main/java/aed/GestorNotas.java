package aed;

import java.util.ArrayList;
import java.util.List;

public class GestorNotas {
    private Heapmin<Nota> heapNotas;
    private Handle<Nota>[] handlesPorEstudiante;
    private int capacidad;
    private int cantidad;

    public static class Nota implements Comparable<Nota> {
        private int estudianteId;
        private double valor;
        private boolean entregado;

        public Nota(int estudianteId, double valor) {
            this.estudianteId = estudianteId;
            this.valor = valor;
            this.entregado = false;
        }

        public Nota(int estudianteId, double valor, boolean entregado) {
            this.estudianteId = estudianteId;
            this.valor = valor;
            this.entregado = entregado;
        }

        public int getEstudianteId() {
            return estudianteId;
        }

        public double getValor() {
            return valor;
        }

        public boolean isEntregado() {
            return entregado;
        }

        public void setEntregado(boolean entregado) {
            this.entregado = entregado;
        }

        @Override
        public int compareTo(Nota otra) {
            // 1. No entregados tienen prioridad sobre entregados
            if (!this.entregado && otra.entregado) return 1;
            if (this.entregado && !otra.entregado) return -1;
            
            // 2. Menor nota tiene prioridad sobre mayor nota
            if (this.nota < otra.nota) return 1;
            if (this.nota > otra.nota) return -1;
            
            // 3. Mayor ID tiene prioridad sobre menor ID
            if this.estudiante>otra.estudiante return 1
            if this.estudiante<otra.estudiante return -1
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Nota nota = (Nota) obj;
            return estudianteId == nota.estudianteId;
        }

        @Override
        public String toString() {
            return String.format("Est%d: %.1f%s", estudianteId, valor, entregado ? " (ent)" : "");
        }
    }

    public GestorNotas(int capacidad) {
        this.capacidad = capacidad;
        this.heapNotas = new Heapmin<>(capacidad);
        this.handlesPorEstudiante = (Handle<Nota>[]) new Handle[capacidad];
        this.cantidad = 0;
    }

    // OPERACIONES DE REGISTRO
    public Handle<Nota> registrarEstudiante(int estudianteId) {
        return registrarEstudiante(estudianteId, 0.0);
    }

    public Handle<Nota> registrarEstudiante(int estudianteId, double notaInicial) {
        if (handlesPorEstudiante[estudianteId] != null) {
            return;
        }

        Nota nuevaNota = new Nota(estudianteId, notaInicial);
        Handle<Nota> handle = heapNotas.agregar(nuevaNota);
        handlesPorEstudiante[estudianteId] = handle;
        cantidad++;
        return handle;
    }

    // OPERACIONES DE ACTUALIZACIÓN (O(log n))
    public boolean actualizarNota(int estudianteId, double nuevaNota) {
        if (!existeEstudiante(estudianteId)) return false;

        Handle<Nota> handle = handlesPorEstudiante[estudianteId];
        Nota notaActualizada = new Nota(estudianteId, nuevaNota, 
                                      ((Nota) ((Heapmin.HandleHeapmin) handle).getElemento()).isEntregado());
        heapNotas.actualizar(handle, notaActualizada);
        return true;
    }

    public void marcarEntregado(int estudianteId) {
        if (!existeEstudiante(estudianteId)) return;

        Handle<Nota> handle = handlesPorEstudiante[estudianteId];
        Nota notaActual = (Nota) ((Heapmin.HandleHeapmin) handle).getElemento();
        Nota notaActualizada = new Nota(estudianteId, notaActual.getValor(), true);
        heapNotas.actualizar(handle, notaActualizada);
    }

    public void eliminarEstudiante(int estudianteId) {
        if (!existeEstudiante(estudianteId)) return;

        Handle<Nota> handle = handlesPorEstudiante[estudianteId];
        handle.eliminar();
        handlesPorEstudiante[estudianteId] = null;
        cantidad--;
    }

    // OPERACIONES DE CONSULTA (O(1))
    public boolean estaEntregado(int estudianteId) {
        if (!existeEstudiante(estudianteId)) return false;
        Handle<Nota> handle = handlesPorEstudiante[estudianteId];
        return ((Nota) ((Heapmin.HandleHeapmin) handle).getElemento()).isEntregado();
    }

    public Double obtenerNota(int estudianteId) {
        if (!existeEstudiante(estudianteId)) return null;
        Handle<Nota> handle = handlesPorEstudiante[estudianteId];
        return ((Nota) ((Heapmin.HandleHeapmin) handle).getElemento()).getValor();
    }

    public Handle<Nota> obtenerHandle(int estudianteId) {
        if (!existeEstudiante(estudianteId)) return null;
        return handlesPorEstudiante[estudianteId];
    }

    // OPERACIONES CON EL HEAP
    public Nota obtenerMenorNota() {
        return heapNotas.obtenerMinimo();
    }

    public Nota sacarMenorNota() {
        Nota menor = heapNotas.sacarMinimo();
        if (menor != null) {
            handlesPorEstudiante[menor.getEstudianteId()] = null;
            cantidad--;
        }
        return menor;
    }

    public List<Nota> obtenerKPeores(int k) {
        List<Nota> peores = new ArrayList<>();
        Heapmin<Nota> copia = crearCopiaHeap();
        
        for (int i = 0; i < k && !copia.estaVacio(); i++) {
            Nota menor = copia.sacarMinimo();
            peores.add(menor);
        }
        return peores;
    }

    // MÉTODOS DE CONSULTA GENERAL
    public List<Double> obtenerTodasNotas() {
        List<Double> todas = new ArrayList<>();
        for (int i = 0; i < capacidad; i++) {
            if (handlesPorEstudiante[i] != null) {
                Handle<Nota> handle = handlesPorEstudiante[i];
                todas.add(((Nota) ((Heapmin.HandleHeapmin) handle).getElemento()).getValor());
            }
        }
        return todas;
    }

    public List<Nota> obtenerEstudiantesEntregados() {
        List<Nota> entregados = new ArrayList<>();
        for (int i = 0; i < capacidad; i++) {
            if (handlesPorEstudiante[i] != null) {
                Handle<Nota> handle = handlesPorEstudiante[i];
                Nota nota = (Nota) ((Heapmin.HandleHeapmin) handle).getElemento();
                if (nota.isEntregado()) {
                    entregados.add(nota);
                }
            }
        }
        return entregados;
    }

    public boolean existeEstudiante(int estudianteId) {
        return estudianteId >= 0 && estudianteId < capacidad && 
               handlesPorEstudiante[estudianteId] != null;
    }

    public int cantidadEstudiantes() {
        return cantidad;
    }

    public boolean estaVacio() {
        return cantidad == 0;
    }

    // MÉTODOS AUXILIARES
    private Heapmin<Nota> crearCopiaHeap() {
        Heapmin<Nota> copia = new Heapmin<>(capacidad);
        for (int i = 0; i < capacidad; i++) {
            if (handlesPorEstudiante[i] != null) {
                Handle<Nota> handle = handlesPorEstudiante[i];
                Nota notaOriginal = (Nota) ((Heapmin.HandleHeapmin) handle).getElemento();
                copia.agregar(new Nota(notaOriginal.getEstudianteId(), 
                                     notaOriginal.getValor(), 
                                     notaOriginal.isEntregado()));
            }
        }
        return copia;
    }

    public void actualizarHandles() {
        // Verificar consistencia de handles
        for (int i = 0; i < capacidad; i++) {
            if (handlesPorEstudiante[i] != null) {
                Heapmin.HandleHeapmin handle = (Heapmin.HandleHeapmin) handlesPorEstudiante[i];
                if (handle.estaEliminado()) {
                    handlesPorEstudiante[i] = null;
                    cantidad--;
                }
            }
        }
    }

    @Override
    public String toString() {
        return heapNotas.toString();
    }
}
