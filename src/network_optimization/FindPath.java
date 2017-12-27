package network_optimization;

import java.util.*;

/**
 *
 * @author Nishant
 */
public class FindPath {
    private boolean[] onPath;
    private Stack<Edge> path;
    int numPaths;
    
    public FindPath(EdgeWeightedGraph G, int s, int t) {
        onPath = new boolean[G.V()];
        path = new Stack<Edge>();
        dfs(G, s, t);
    }
    
    private void dfs(EdgeWeightedGraph G, int s, int t) {
        //StdOut.println("s = "+s);
        onPath[s] = true;
        if(s==t) {
            //found = true;
            return;
        }
        else {
            for (Edge u: G.adj(s)) {
                //StdOut.println(onPath[u.other(s)]);
                if(!onPath[u.other(s)]) {
                   //StdOut.println(u.other(s));
                   path.push(u);
                   dfs(G,u.other(s),t);
                   if(onPath[t])
                       return;
                }
            }
        } 
        
    }
    
    public Stack<Edge> getPath() {
        return path;
    }
    
    public int getBW() {
        int bw = 0;
        if(!path.isEmpty()) {
            for(Edge e: path) {
                bw += e.weight();
            }
        }
        return bw;
    }

}
