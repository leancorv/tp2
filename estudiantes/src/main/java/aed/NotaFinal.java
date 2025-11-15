package aed;

public class NotaFinal implements Comparable<NotaFinal> {
    public double _nota;
    public int _id;

    public NotaFinal(double nota, int id){
        _nota = nota;
        _id = id;
    }

    public int compareTo(NotaFinal otra){
        if (otra._id != this._id){
            return this._id - otra._id;
        }
        return Double.compare(this._nota, otra._nota);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NotaFinal other = (NotaFinal) obj;
        return Double.compare(this._nota, other._nota) == 0 && this._id == other._id;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(_nota, _id);
    }
    
        
}
