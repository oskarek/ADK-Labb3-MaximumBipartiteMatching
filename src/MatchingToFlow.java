

/**
 * Main class of the program.
 * Created by Robert Lorentz on 2016-10-30.
 */
public class MatchingToFlow {
    public Graph translate(Graph graph, int x, int y) {
        int s = graph.getS();
        graph.addVertex(s);
        for(int i = 1; i<=x; i++) {
            graph.addEdge(s, i, 1);
        }

        int t = graph.getT();
        graph.addVertex(t);
        for(int i = x+1; i<=x+y; i++) {
            graph.addEdge(i, t, 1);
        }

        return graph;
    }
}