package network_optimization;
import java.io.*;
/**
 *
 * @author Nishant
 */
public class Edge implements Comparable<Edge> {
    private final int v;
    private final int u;
    private final int weight;

   
    public Edge(int v, int u, int weight) {
        
        this.v = v;
        this.u = u;
        this.weight = weight;
    }

    
    public int weight() {
        return weight;
    }

    
    public int either() {
        return v;
    }

    
    public int other(int vertex) {
        if      (vertex == v) return u;
        else if (vertex == u) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }
    
    

    
    public int compareTo(Edge that) {
        return Integer.compare(this.weight, that.weight);
    }

    
    public String toString() {
        return String.format("%d-%d %d", v, u, weight);
    }

}
