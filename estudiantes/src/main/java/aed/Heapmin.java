package aed;

import java.util.ArrayList;

public class Heapmin<T extends Comparable<T>> {
    private Handle<T>[] heap;
    private int size;
    private ArrayList<Handle<T>> todosHandles;

    public class HandleHeapmin implements Handle<T> {
        private int posicion;
        private T elemento;
        private boolean eliminado;

        public HandleHeapmin(T elem, int pos) {
            this.elemento = elem;
            this.posicion = pos;
            this.eliminado = false;
        }

        public int getPosicion() {
            return this.posicion;
        }

        public T getElemento() {
            return this.elemento;
        }

        public void setPosicion(int pos) {
            this.posicion = pos;
        }

        public void setElemento(T elem) {
            this.elemento = elem;
        }

        @Override
        public T valor() {
            return this.elemento;
        }

        @Override
        public void eliminar() {
            if (eliminado) return;
            
            this.eliminado = true;
            int pos = this.posicion;
            
            // Si no es el último elemento, intercambiar con el último y hacer siftDown
            if (pos < size - 1) {
                swap(pos, size - 1);
                heap[size - 1] = null;
                size--;
                siftDown(pos);
            } else {
                // Si es el último, simplemente remover
                heap[pos] = null;
                size--;
            }
        }

        public boolean estaEliminado() {
            return eliminado;
        }
    }

    public Heapmin(int capacidad) {
        this.heap = (Handle<T>[]) new Handle[capacidad]; 
        this.todosHandles = new ArrayList<>();
        this.size = 0;
    }

    public Heapmin(Handle<T>[] elementos, int cantidad) {
        this.heap = elementos;
        this.size = cantidad;
        this.todosHandles = new ArrayList<>();
        
        // Inicializar lista de handles
        for (int i = 0; i < size; i++) {
            todosHandles.add(heap[i]);
            ((HandleHeapmin) heap[i]).setPosicion(i);
        }
        
        for (int i = (size - 1) / 2; i >= 0; i--) {
            siftDown(i);
        }
    }

    public Handle<T> agregar(T elem) {
        if (size >= heap.length) {
            redimensionar();
        }
        
        HandleHeapmin h = new HandleHeapmin(elem, size);
        this.heap[this.size] = h;
        this.todosHandles.add(h);
        siftUp(size);
        this.size++;
        return h;
    }

    public T obtenerMinimo() {
        if (this.size == 0) {
            return null;
        }
        return this.heap[0].valor();
    }

    public T sacarMinimo() {
        if (this.size == 0) {
            return null;
        }
        T minimo = heap[0].valor();
        heap[0].eliminar(); // Usa el método eliminar del handle
        return minimo;
    }

    public T sacarMinimoSinEliminar() {
        if (this.size == 0) {
            return null;
        }
        
        T minimo = heap[0].valor();
        
        // Lógica de sacar sin marcar como eliminado
        swap(0, size - 1);
        size--;
        if (size > 0) {
            siftDown(0);
        }
        
        return minimo;
    }

    public void actualizar(Handle<T> h, T nuevoValor) {
        
        HandleHeapmin handle = (HandleHeapmin) h;
        if (handle.estaEliminado()) return;
        
        T valorAnterior = handle.getElemento();
        handle.setElemento(nuevoValor);
        
        int pos = handle.getPosicion();
        if (nuevoValor.compareTo(valorAnterior) < 0) {
            siftUp(pos);
        } else {
            siftDown(pos);
        }
    }

    public int tamaño() {
        return size;
    }

    public boolean estaVacio() {
        return size == 0;
    }

    public ArrayList<Handle<T>> obtenerTodosHandles() {
        return new ArrayList<>(todosHandles);
    }

    private void siftUp(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (heap[i].valor().compareTo(heap[padre].valor()) < 0) {
                swap(i, padre);
                i = padre;
            } else {
                break;
            }
        }
    }

    private void siftDown(int i) {
        while (2*i+1 < this.size) {
            int izq = 2*i+1;
            int der = 2*i+2;
            int menor = i;

            if (heap[izq].valor().compareTo(heap[menor].valor()) < 0) {
                menor = izq;
            }

            if (der < this.size && 
                heap[der].valor().compareTo(heap[menor].valor()) < 0) {
                menor = der;
            }

            if (menor != i) {
                swap(i, menor);
                i = menor;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        Handle<T> temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        ((HandleHeapmin) heap[i]).setPosicion(i);
        ((HandleHeapmin) heap[j]).setPosicion(j);
    }

    private void redimensionar() {
        Handle<T>[] nuevoHeap = (Handle<T>[]) new Handle[heap.length * 2];
        System.arraycopy(heap, 0, nuevoHeap, 0, heap.length);
        heap = nuevoHeap;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            if (!((HandleHeapmin) heap[i]).estaEliminado()) {
                sb.append(heap[i].valor().toString());
                if (i < size - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
