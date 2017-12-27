package network_optimization;

import java.util.Random;
import java.util.Stack;

/**
 *
 * @author Nishant
 */
public class DijkstraWithHeap {
    private int[] status;
    private int[] bw;
    private int[] dad;
    private int dist;
    
    public DijkstraWithHeap(EdgeWeightedGraph G, int s, int t) {    //EdgeWeightedGraph g, int s, int t
        status = new int[5000];
        for(int i=0; i<5000; i++) {
            status[i] = 2;
        }
        status[s] = 0;
        bw = new int[5000];
        for(int i=0; i<5000; i++) {
            bw[i] = 0;
        }
        dad = new int[5000];
        for(int i=0; i<5000; i++) {
            dad[i] = -1;
        }
        bw[s] = Integer.MAX_VALUE;
        HeapDijkstra maxHeap = new HeapDijkstra(G.V());
        for (Edge u: G.adj(s)) {
            bw[u.other(s)] = u.weight();
            status[u.other(s)] = 1;
            dad[u.other(s)] = s;
            maxHeap.insert(u.other(s),bw[u.other(s)]);
        }
        
        while(status[t] != 0) {
            int v = maxHeap.delMax();
            status[v] = 0;
            //StdOut.println("fringe is "+v);
            for (Edge e: G.adj(v)) {
                int w = e.other(v);
                if(status[w] == 2) {
                    bw[w] = bw[v] + e.weight();
                    dad[w] = v;
                    status[w] = 1;
                    maxHeap.insert(w,bw[w]);
                    //StdOut.println("neighbour is "+w);
                }
                else if(status[w] == 1 && bw[w] < bw[v] + e.weight()) {
                    maxHeap.delete(w);
                    bw[w] = bw[v] + e.weight();
                    //StdOut.println("bandwidth is "+bw[w]);
                    dad[w] = v;
                    maxHeap.insert(w,bw[w]);
                }
            }
        }
        dist = bw[t];
    }
    
    public int[] getDad() {
        return dad;
    }
    
    public int getDist() {
        return dist;
    }
    
    public int getStatus(int index) {
        return status[index];
    }
    
    private static int getRandomNumberInRange(int min, int max) {

	if (min >= max) {
		throw new IllegalArgumentException("max must be greater than min");
	}

	Random r = new Random();
	return r.nextInt((max - min) + 1) + min;
    }
    
    public static void main(String[] args) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(0);
        int s = getRandomNumberInRange(0,4999);
        int t = getRandomNumberInRange(0,4999);
        StdOut.println("s is "+s+" and t is "+t);
        DijkstraWithHeap d = new DijkstraWithHeap(G,s,t);
        int[] path= d.getDad();
        int BW = d.getDist();
        for(int i=0; i<5000; i++)
            if(path[i] != -1)
                StdOut.println(path[i]);
        StdOut.println("Maximum Bandwidth is "+BW);
        
    }
}
