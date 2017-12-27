package network_optimization;

import java.util.Random;
import java.util.Stack;

/**
 *
 * @author Nishant
 */
public class Kruskal {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight;                        
    private Queue<Edge> mst = new Queue<Edge>();  
    private EdgeWeightedGraph mst_graph;

    public Kruskal(EdgeWeightedGraph G) {
        MaxHeap<Edge> heap = new MaxHeap<Edge>();
        for (Edge e : G.edges()) {
            heap.insert(e);
        }
        
        heap.sort();
        
        Union_Find uf = new Union_Find(G.V());
        mst_graph = new EdgeWeightedGraph();
        while (!heap.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = heap.delMax();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) { 
                uf.union(v, w);  
                mst.enqueue(e);  
                mst_graph.addEdge(e);
                weight += e.weight();
            }
        }

        assert check(G);
    }

    public Iterable<Edge> edges() {
        return mst;
    }
    
    public EdgeWeightedGraph getMST_Graph() {
        return mst_graph;
    }

    public double weight() {
        return weight;
    }
    
    
    private boolean check(EdgeWeightedGraph G) {

        
        double total = 0.0;
        for (Edge e : edges()) {
            total += e.weight();
        }
        if (Math.abs(total - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, weight());
            return false;
        }

        
        Union_Find uf = new Union_Find(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

       
        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        
        for (Edge e : edges()) {

           
            uf = new Union_Find(G.V());
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }
            
           
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (!uf.connected(x, y)) {
                    if (f.weight() > e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }
    
    private static int getRandomNumberInRange(int min, int max) {

	if (min >= max) {
		throw new IllegalArgumentException("max must be greater than min");
	}

	Random r = new Random();
	return r.nextInt((max - min) + 1) + min;
    }

    public static void main(String[] args) {
     
        EdgeWeightedGraph G = new EdgeWeightedGraph(1);
        Kruskal mst = new Kruskal(G);
        EdgeWeightedGraph mst_graph = mst.getMST_Graph();
        int s = getRandomNumberInRange(0,4999);
        int t = getRandomNumberInRange(0,4999);;
        FindPath path;
        Stack<Edge> edges;
        int BW=0;
        if(s!=t) {
            path = new FindPath(mst_graph,s,t);
            edges = path.getPath();
            StdOut.println("s = "+s+", t = "+t);
            for(Edge e: edges) {
                StdOut.println(e);
                BW = BW+e.weight();
            }
            StdOut.printf("%d\n", BW);
        }
        
        
    }
}
