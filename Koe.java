import java.util.Iterator;

class Koe<T> implements Iterable<T>{
    
    class Node {
        Node neste = null;
        Node forrige = null;
        T data;
        Node (T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    class KoeIterator implements Iterator<T> {
        private int pos = 0;

        @Override
        public T next() {
            pos++;
            return hent(pos - 1);
        }

        @Override
        public boolean hasNext() { return pos < storrelse(); }
    }

    protected Node start = null;
    protected Node slutt = null;
    protected int storrelse;

    @Override
    public Iterator<T> iterator() {
        return new KoeIterator();
    }

    public int storrelse() {
        return storrelse;
    }

    public void leggTil(T x) {
        Node ny = new Node(x);

        if (start == null) {
            start = ny;
            slutt = ny;
        }
        else {
            start.forrige = ny;
            ny.neste = start;
            start = ny;
        }
        storrelse += 1;
    }

    public T hent (int pos) throws IndexOutOfBoundsException {
        if (pos > storrelse - 1 || pos < 0){ throw new IndexOutOfBoundsException(pos); }
        
        int teller = 0;
        Node sjekk = start;

        while (teller != pos) {
            sjekk = sjekk.neste;
            teller++;
        }
        return sjekk.data;

    }

    public T fjern() throws IndexOutOfBoundsException{
        if (storrelse == 0) { throw new IndexOutOfBoundsException(0); }

        T returverdi = slutt.data;
        if (storrelse == 1) {
            start = null;
            slutt = null;
        }
        else {
            slutt.forrige.neste = null;
            slutt = slutt.forrige;
        }
        storrelse -= 1;
        return returverdi;
    }

    @Override
    public String toString() { 
        String utskrift = "[";
        int teller = 0;
        for (T e : this) {
            if (teller != storrelse - 1) utskrift += e + " , ";
            else utskrift += e;
        }
        utskrift += "]";
        return utskrift;
    }

}

