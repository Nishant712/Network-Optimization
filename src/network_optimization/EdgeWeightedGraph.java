package network_optimization;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
/**
 *
 * @author Nishant
 */
public class EdgeWeightedGraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V = 5000;
    private int E = 0;
    
    private MultiSet<Edge>[] adj;
    
    private static int getRandomNumberInRange(int min, int max) {

	if (min >= max) {
		throw new IllegalArgumentException("max must be greater than min");
	}

	Random r = new Random();
	return r.nextInt((max - min) + 1) + min;
    }
    
    public EdgeWeightedGraph() {
        adj = (MultiSet<Edge>[]) new MultiSet[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new MultiSet<Edge>();
        }
    }
    
    public EdgeWeightedGraph(int c) {
        adj = (MultiSet<Edge>[]) new MultiSet[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new MultiSet<Edge>();
        }
        if(c==0) {
            for (int i = 0; i < V/2; i++) {
                while(degree(i) < 8) {
                    int j = getRandomNumberInRange(0,V-1);
                    if(j!=i && edgeExists(i,j) == false && degree(j)<8) {
                        int weight = getRandomNumberInRange(1,1000);
                        Edge e = new Edge(i, j, weight);
                        addEdge(e);
                    }
                }
            }  
        }
        else if(c==1) {
            for (int i = 0; i < V/2; i++) {
                while(degree(i) < 0.2*V) {
                    int j = getRandomNumberInRange(0,V-1);
                    if(j!=i && edgeExists(i,j) == false && degree(j)<0.2*V) {
                        int weight = getRandomNumberInRange(1,1001);
                        Edge e = new Edge(i, j, weight);
                        addEdge(e);
                    }
                }
            }
        }
           
    }
    
    public boolean edgeExists(int v, int u) {
        
            for (Edge e : adj(v)) {
                if (e.other(v) == u) {
                    return true;
                }
            }
        
        return false;
    }

    
    public int V() {
        return V;
    }
    
    public void addEdge(Edge e) {
        int v = e.either();
        int u = e.other(v);
        adj[v].add(e);
        adj[u].add(e);
        E++;
    }
    
    public int getE() {
        return E;
    }

    
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    
    public int degree(int v) {
        return adj[v].size();
    }

    
    public Iterable<Edge> edges() {
        MultiSet<Edge> list = new MultiSet<Edge>();
        for (int v = 0; v < V; v++) {
          
            for (Edge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    }
    
    public Edge[] edgesArr() {
        Edge[] edges = new Edge[E];
        int i = 0;
        for (int v = 0; v < V; v++) {
          
            for (Edge e : adj(v)) {
                edges[i] = e;
            }
        }
        return edges;
    }


}
