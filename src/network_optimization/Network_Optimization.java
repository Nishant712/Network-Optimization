package network_optimization;

import java.util.Random;

/**
 *
 * @author Nishant
 */
public class Network_Optimization {

    private static int getRandomNumberInRange(int min, int max) {

	if (min >= max) {
		throw new IllegalArgumentException("max must be greater than min");
	}

	Random r = new Random();
	return r.nextInt((max - min) + 1) + min;
    }
    
    
    public static void main(String[] args) {
        EdgeWeightedGraph sparseGraph = new EdgeWeightedGraph(0);
        EdgeWeightedGraph denseGraph = new EdgeWeightedGraph(1);
        for(int i=1; i<=5; i++) {
            int s = getRandomNumberInRange(0,4999);
            int t = getRandomNumberInRange(0,4999);
            StdOut.println("s is "+s+" and t is "+t);
            FindPath path1;
            FindPath path2;
            long start_time = System.nanoTime();
            Kruskal mst = new Kruskal(sparseGraph);
            EdgeWeightedGraph mst_graph1 = mst.getMST_Graph();
            path1 = new FindPath(mst_graph1,s,t);
            long end_time = System.nanoTime();
            double difference1 = (end_time - start_time) / 1e6;
            start_time = System.nanoTime();
            Kruskal mst2 = new Kruskal(denseGraph);
            EdgeWeightedGraph mst_graph2 = mst2.getMST_Graph();
            path2 = new FindPath(mst_graph2,s,t);
            end_time = System.nanoTime();
            double difference2 = (end_time - start_time) / 1e6;
            start_time = System.nanoTime();
            Dijkstra d1 = new Dijkstra(sparseGraph,s,t);
            end_time = System.nanoTime();
            double difference3 = (end_time - start_time) / 1e6;
            start_time = System.nanoTime();
            DijkstraWithHeap d2 = new DijkstraWithHeap(sparseGraph,s,t);
            end_time = System.nanoTime();
            double difference4 = (end_time - start_time) / 1e6;
            start_time = System.nanoTime();
            Dijkstra d3 = new Dijkstra(denseGraph,s,t);
            end_time = System.nanoTime();
            double difference5 = (end_time - start_time) / 1e6;
            start_time = System.nanoTime();
            DijkstraWithHeap d4 = new DijkstraWithHeap(denseGraph,s,t);
            end_time = System.nanoTime();
            double difference6 = (end_time - start_time) / 1e6;
            StdOut.println("Time taken to maximum bandwidth path using Kruskal's algorithm for sparse graph is "+difference1);
            StdOut.println("Time taken to maximum bandwidth path using Kruskal's algorithm for dense graph is "+difference2);
            StdOut.println("Time taken to maximum bandwidth path using Dijkstra's algorithm without heap for sparse graph is "+difference3);
            StdOut.println("Time taken to maximum bandwidth path using Dijkstra's algorithm with heap for sparse graph is "+difference4);
            StdOut.println("Time taken to maximum bandwidth path using Dijkstra's algorithm without heap for dense graph is "+difference5);
            StdOut.println("Time taken to maximum bandwidth path using Dijkstra's algorithm with heap for dense graph is "+difference6);
        }
    }
    
}
