package network_optimization;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 *
 * @author Nishant
 */
public class MaxHeap<Key> implements Iterable<Key> {
    private Key[] pq;                    
    private int n;                       
    private Comparator<Key> comparator;  

    public MaxHeap(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }
    
    public MaxHeap() {
        this(1);
    }

    public MaxHeap(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    public MaxHeap(Comparator<Key> comparator) {
        this(1, comparator);
    }

    public MaxHeap(Key[] keys) {
        n = keys.length;
        pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < n; i++) {
            pq[i+1] = keys[i];
            
        }
        for (int k = n/2; k >= 1; k--)
            heapify(k);
        assert isMaxHeap();
    }
      
    
    public void sort() {
        for (int k = n/2; k >= 1; k--)
            heapify(k);
    }


    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }


    private void resize(int capacity) {
        assert capacity > n;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public void insert(Key x) {

        
        if (n == pq.length - 1) resize(2 * pq.length);

        
        pq[++n] = x;
       
        assert isMaxHeap();
    }

    public Key delMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Key max = pq[1];
        swap(1, n--);
        heapify(1);
        pq[n+1] = null;     
        if ((n > 0) && (n == (pq.length - 1) / 4)) resize(pq.length / 2);
        assert isMaxHeap();
        return max;
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

    private boolean less(int i, int j) {
        if (comparator == null) {
            if(pq[i] == null || pq[j] == null)
                System.out.println("pq is null");
            int c = ((Comparable<Key>) pq[i]).compareTo(pq[j]);
            return c < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    private void swap(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }
    
    

 
    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    
    private boolean isMaxHeap(int k) {
        if (k > n) return true;
        int left = 2*k;
        int right = 2*k + 1;
        if (left  <= n && less(k, left))  return false;
        if (right <= n && less(k, right)) return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }

    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {

        private MaxHeap<Key> copy;

        public HeapIterator() {
            if (comparator == null) copy = new MaxHeap<Key>(size());
            else                    copy = new MaxHeap<Key>(size(), comparator);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }
    }

}
