import java.util.ArrayList;

/**
 * Main class of the program.
 * Created by Robert Lorentz on 2016-10-30.
 */
public class MatchingToFlow {
    public Graph translate(Graph graph) {
        ArrayList<Graph.Vertex> xVertexes = graph.getxVertexes();
        ArrayList<Graph.Vertex> yVertexes = graph.getyVertexes();
        int s = graph.getS();
        graph.addS(s);
        for(Graph.Vertex vertex : xVertexes) {
            if(vertex.getRepresentation() != s) {
                graph.addEdge(0, s, vertex.getRepresentation(), 1);
                //graph.addEdge(0, vertex.getRepresentation(), s, 0);
            }
        }
        int t = graph.getT();
        graph.addT(t);
        for(Graph.Vertex vertex : yVertexes) {
            if(vertex.getRepresentation() != t) {
                //graph.addEdge(1, t, vertex.getRepresentation(), 0);
                graph.addEdge(1, vertex.getRepresentation(), t, 1);
            }
        }
        return graph;
    }
}