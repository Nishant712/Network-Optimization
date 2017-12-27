package network_optimization;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 *
 * @author Nishant
 */
public class HeapDijkstra<Key extends Comparable<Key>> implements Iterable<Integer> {
    private int n;           
    private int[] pq;        
    private int[] qp;        
    private Key[] keys;     

    public HeapDijkstra(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        n = 0;
        keys = (Key[]) new Comparable[maxN + 1];    // make this of length maxN??
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];                   // make this of length maxN??
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean contains(int i) {
        return qp[i] != -1;
    }

    public int size() {
        return n;
    }

    public void insert(int i, Key key) {
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        trickleUp(n);
    }

    public int maxIndex() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    public Key maxKey() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return keys[pq[1]];
    }

    public int delMax() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        swap(1, n--);
        heapify(1);

        assert pq[n+1] == min;
        qp[min] = -1;        
        keys[min] = null;    
        pq[n+1] = -1;        
        return min;
    }

    public Key keyOf(int i) {
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return keys[i];
    }

    public void delete(int i) {
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        swap(index, n--);
        trickleUp(index);
        heapify(index);
        keys[i] = null;
        qp[i] = -1;
    }

    private boolean less(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
    }

    private void swap(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void trickleUp(int k) {
        while (k > 1 && less(k/2, k)) {
            swap(k, k/2);
            k = k/2;
        }
    }

    private void heapify(int index) {
        while (2*index <= n) {
            int leftChild = 2*index;
            int rightChild = leftChild+1;
            if (leftChild < n && less(leftChild, rightChild)) leftChild++;
            if (!less(index, leftChild)) break;
            swap(index, leftChild);
            index = leftChild;
        }
    }

    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {
        private HeapDijkstra<Key> copy;

        public HeapIterator() {
            copy = new HeapDijkstra<Key>(pq.length - 1);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i], keys[pq[i]]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }
    }
}
